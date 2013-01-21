package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import lawscraper.client.ClientFactory;
import lawscraper.client.place.DocumentPlace;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.events.*;
import lawscraper.client.ui.panels.lawpanel.LawPanel;
import lawscraper.shared.DocumentBookMarkRequestFactory;
import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.UserRequestFactory;
import lawscraper.shared.proxies.DocumentBookMarkProxy;
import lawscraper.shared.proxies.HTMLProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/26/12
 * Time: 9:49 PM
 */
public class DocumentViewActivity extends AbstractActivity implements LawView.Presenter {
    final LawRequestFactory requests = GWT.create(LawRequestFactory.class);
    final DocumentBookMarkRequestFactory bookMarkRequests = GWT.create(DocumentBookMarkRequestFactory.class);
    final UserRequestFactory userRequestFactory = GWT.create(UserRequestFactory.class);
    private ClientFactory clientFactory;
    private DocumentPlace place;
    private EventBus eventBus;

    public DocumentViewActivity(DocumentPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;
    }


    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        requests.initialize(eventBus);
        bookMarkRequests.initialize(eventBus);
        userRequestFactory.initialize(eventBus);

        StartView startView = clientFactory.getStartView();
        panel.setWidget(startView.asWidget());
        subscribeToChangeUserEvent(eventBus);
        subscribeToChangeLegalResearchEvent(eventBus);

        /*
        for (String lawKey : clientFactory.getClientCache().getTabs()) {
            getDocument(lawKey);
        }
        */

        getDocument(place.getDocumentKey());
    }

    /**
     * If the current user is changed. Update the view accordingly.
     *
     * @param eventBus
     */
    private void subscribeToChangeUserEvent(EventBus eventBus) {
        eventBus.addHandler(SetCurrentUserEvent.TYPE, new SetCurrentUserEventHandler() {
            @Override
            public void onSetCurrentUser(SetCurrentUserEvent event) {
                //clientFactory.getRoleBasedWidgetHandler().handleRoleBasedViews(LawViewImpl.class);
            }
        });
    }

    /**
     * If the current legal research is changed. Update the view accordingly.
     *
     * @param eventBus
     */
    private void subscribeToChangeLegalResearchEvent(EventBus eventBus) {
        eventBus.addHandler(SetCurrentLegalResearchEvent.TYPE, new SetCurrentLegalResearchEventHandler() {

            @Override
            public void onSetCurrentLegalResearch(SetCurrentLegalResearchEvent event) {
                getDocument(place.getDocumentKey());
            }
        });
    }

    /**
     * Get the document by document key. Either by client storage or from server
     *
     * @param key
     */
    @Override
    public void getDocument(final String key) {
        LawRequestFactory.LawRequest context = requests.lawRequest();
        setCursor(Style.Cursor.WAIT);

        final StartView startView = clientFactory.getStartView();
        //select the tab if it exists instead of getting it from the server
        if (startView.selectDocumentTabIfExists(key)) {
            setCursor(Style.Cursor.DEFAULT);
            return;
        }

        //fetch from client storage
        final HTMLProxy result = clientFactory.getClientCache().get(key);
        if (result != null) {
            startView.addDocument(result);

            showBookMarks(place.getDocumentKey());

            System.out.println("Got the cached law");

            //fire event with client result
            eventBus.fireEvent(new SetCurrentLawEvent(result));
        } else {
            //fetch from server
            context.findLawHTMLWrappedByLawKey(place.getDocumentKey()).fire(new Receiver<HTMLProxy>() {
                @Override
                public void onSuccess(HTMLProxy result) {
                    //fire event with server result
                    eventBus.fireEvent(new SetCurrentLawEvent(result));

                    //add result to the html5 client cache
                    clientFactory.getClientCache().add(key, result);

                    //set default cursor
                    setCursor(Style.Cursor.DEFAULT);

                    //add book marks to the document
                    showBookMarks(place.getDocumentKey());
                    System.out.println("Got the law from the server");
                }

                @Override
                public void onFailure(ServerFailure error) {
                    setCursor(Style.Cursor.DEFAULT);
                    System.out.println("DocumentViewActivity::getDocument()" + error.getMessage());
                }
            });
        }

        setCursor(Style.Cursor.DEFAULT);
    }

    private void setCursor(Style.Cursor cursor) {
        RootPanel.get().getElement().getStyle().setCursor(cursor);
    }


    private void showBookMarks(String lawKey) {
        DocumentBookMarkRequestFactory.DocumentBookMarkRequest context = bookMarkRequests.documentBookMarkRequest();
        context.findBookMarksByLawKey(lawKey).fire(new Receiver<List<DocumentBookMarkProxy>>() {
            @Override
            public void onSuccess(List<DocumentBookMarkProxy> response) {
                //lawView.getBookMarkPanel().setBookMarks(response);
                //lawView.getLawPanel().setBookMarkMarkings(response);
            }
        });
    }

    @Override
    public DocumentBookMarkProxy createBookMarkRequestProxy() {
        DocumentBookMarkRequestFactory.DocumentBookMarkRequest documentBookMarkRequest =
                bookMarkRequests.documentBookMarkRequest();
        DocumentBookMarkProxy documentBookMarkProxy = documentBookMarkRequest.create(DocumentBookMarkProxy.class);
        return documentBookMarkProxy;
    }

    @Override
    public void addBookMark(final Long lawDocumentPartId) {
        DocumentBookMarkRequestFactory.DocumentBookMarkRequest context = bookMarkRequests.documentBookMarkRequest();
        context.addBookMark(lawDocumentPartId).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                System.out.println("Added bookmark");
                updateBookMarkPanel();
            }
        });
    }

    private void updateBookMarkPanel() {
        DocumentBookMarkRequestFactory.DocumentBookMarkRequest context = bookMarkRequests.documentBookMarkRequest();
        context.findBookMarksByLawKey(place.getDocumentKey()).fire(new Receiver<List<DocumentBookMarkProxy>>() {
            @Override
            public void onSuccess(List<DocumentBookMarkProxy> response) {
     //           lawView.getBookMarkPanel().setBookMarks(response);
            }
        });
    }

    @Override
    public void removeBookMark(final Long lawDocumentPartId) {
        DocumentBookMarkRequestFactory.DocumentBookMarkRequest context = bookMarkRequests.documentBookMarkRequest();
        context.removeBookMark(lawDocumentPartId).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                System.out.println("Removed bookmark");
                updateBookMarkPanel();
            }
        });
    }

    @Override
    public void addLawDocument(LawPanel lawPanel, String name, String lawKey) {

    }


    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
