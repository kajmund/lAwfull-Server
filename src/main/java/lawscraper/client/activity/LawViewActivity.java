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
import lawscraper.client.place.LawPlace;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.LawViewImpl;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.events.*;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedFlowPanel;
import lawscraper.shared.DocumentBookMarkRequestFactory;
import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.UserRequestFactory;
import lawscraper.shared.proxies.DocumentBookMarkProxy;
import lawscraper.shared.proxies.HTMLProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:49 PM
 */
public class LawViewActivity extends AbstractActivity implements LawView.Presenter {
    final LawRequestFactory requests = GWT.create(LawRequestFactory.class);
    final DocumentBookMarkRequestFactory bookMarkRequests = GWT.create(DocumentBookMarkRequestFactory.class);
    final UserRequestFactory userRequestFactory = GWT.create(UserRequestFactory.class);
    private ClientFactory clientFactory;
    private LawView lawView;
    private LawPlace place;
    private EventBus eventBus;

    public LawViewActivity(LawPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;

        LawView lawView = clientFactory.getLawView();
        lawView.setPresenter(this);
        this.lawView = lawView;

        for (RoleBasedFlowPanel widget : lawView.getLawPanel().getRoleBasedWidgets()) {
            clientFactory.getRoleBasedWidgetHandler().addRoleBaseWidget(widget, LawViewImpl.class);
        }
    }


    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.eventBus = eventBus;
        requests.initialize(eventBus);
        bookMarkRequests.initialize(eventBus);
        userRequestFactory.initialize(eventBus);

        StartView startView = clientFactory.getStartView();
        panel.setWidget(startView.asWidget());

        startView.getMainContainer().clear();
        startView.getMainContainer().add(lawView);
        clientFactory.getRoleBasedWidgetHandler().handleRoleBasedViews(LawViewImpl.class);
        subscribeToChangeUserEvent(eventBus);
        subscribeToChangeLegalResearchEvent(eventBus);
        getLaw();
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
                clientFactory.getRoleBasedWidgetHandler().handleRoleBasedViews(LawViewImpl.class);

                //getLaw();
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
                getLaw();
            }
        });
    }


    @Override
    public void getLaw() {
        LawRequestFactory.LawRequest context = requests.lawRequest();
        RootPanel.get().getElement().getStyle().setCursor(Style.Cursor.WAIT);

        //select the tab if it exists instead of getting it from the server
        if (lawView.selectLawIfExists(place.getLawKey())) {
            RootPanel.get().getElement().getStyle().setCursor(Style.Cursor.DEFAULT);
            return;
        }

        context.findLawHTMLWrappedByLawKey(place.getLawKey()).fire(new Receiver<HTMLProxy>() {
            @Override
            public void onSuccess(HTMLProxy result) {
                lawView.setLaw(result);
                eventBus.fireEvent(new SetCurrentLawEvent(lawView.getDynamicFlerpContainer()));
                RootPanel.get().getElement().getStyle().setCursor(Style.Cursor.DEFAULT);
            }

            @Override
            public void onFailure(ServerFailure error) {
                RootPanel.get().getElement().getStyle().setCursor(Style.Cursor.DEFAULT);
                System.out.println("LawViewActivity::getLaw()" + error.getMessage());
            }
        });
    }


    private void showBookMarks(String lawKey) {
        DocumentBookMarkRequestFactory.DocumentBookMarkRequest context = bookMarkRequests.documentBookMarkRequest();
        context.findBookMarksByLawKey(lawKey).fire(new Receiver<List<DocumentBookMarkProxy>>() {
            @Override
            public void onSuccess(List<DocumentBookMarkProxy> response) {
                lawView.getBookMarkPanel().setBookMarks(response);
                lawView.getLawPanel().setBookMarkMarkings(response);
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
        context.findBookMarksByLawKey(place.getLawKey()).fire(new Receiver<List<DocumentBookMarkProxy>>() {
            @Override
            public void onSuccess(List<DocumentBookMarkProxy> response) {
                lawView.getBookMarkPanel().setBookMarks(response);
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
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
