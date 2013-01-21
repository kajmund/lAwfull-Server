package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import lawscraper.client.ClientFactory;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.StartViewImpl;
import lawscraper.client.ui.events.*;
import lawscraper.client.ui.panels.documentsearchpanel.DocumentResultElement;
import lawscraper.shared.*;
import lawscraper.shared.proxies.*;
import lawscraper.shared.scraper.LawScraperSource;

import java.util.List;


public class StartViewActivity extends AbstractActivity implements StartView.Presenter {
    final LawRequestFactory lawRequests = GWT.create(LawRequestFactory.class);
    final CaseLawRequestFactory caseLawRequests = GWT.create(CaseLawRequestFactory.class);

    final UserRequestFactory userRequests = GWT.create(UserRequestFactory.class);
    final LegalResearchRequestFactory legalResearchRequests = GWT.create(LegalResearchRequestFactory.class);

    final DocumentPartRequestFactory documentPartRequestFactory = GWT.create(DocumentPartRequestFactory.class);

    //Scrapers request
    final LawScraperRequestFactory lawScraperRequests = GWT.create(LawScraperRequestFactory.class);
    final CaseLawScraperRequestFactory caseLawScraperRequests = GWT.create(CaseLawScraperRequestFactory.class);


    // Used to obtain views, eventBus, placeController
    private ClientFactory clientFactory;
    private EventBus eventBus;
    private FlowPanel flerpContainer;

    public StartViewActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }


    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        final StartView startView = clientFactory.getStartView();
        containerWidget.setWidget(startView.asWidget());

        //init with the eventbus - pls remember this.
        lawRequests.initialize(eventBus);
        caseLawRequests.initialize(eventBus);

        lawScraperRequests.initialize(eventBus);
        caseLawScraperRequests.initialize(eventBus);
        userRequests.initialize(eventBus);
        legalResearchRequests.initialize(eventBus);
        documentPartRequestFactory.initialize(eventBus);

        this.eventBus = eventBus;
        subscribeToChangeUserEvent(eventBus);
        subscribeToChangeLawEvent(eventBus);
        startView.setPresenter(this);
        getLegalResearchByLoggedInUser();
        checkCurrentUser();
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
                clientFactory.getRoleBasedWidgetHandler().handleRoleBasedViews(StartViewImpl.class);
                getLegalResearchByLoggedInUser();
            }
        });
    }

    private void subscribeToChangeLawEvent(EventBus eventBus) {
        eventBus.addHandler(SetCurrentLawEvent.TYPE, new SetCurrentLawEventHandler() {
            @Override
            public void onSetCurrentLaw(SetCurrentLawEvent event) {
                clientFactory.getStartView().addDocument(event.getResult());
            }
        });
    }

    @Override
    public void checkCurrentUser() {
        UserRequestFactory.UserRequest context = userRequests.userRequest();
        context.getCurrentUser().fire(new Receiver<UserProxy>() {
            @Override
            public void onSuccess(UserProxy response) {
                if (response != null && response.getUserRole() != UserRole.Anonymous) {
                    clientFactory.getStartView().setUserLoggedIn(response);
                }
                clientFactory.getRoleBasedWidgetHandler().setUserProxy(response);
                eventBus.fireEvent(new SetCurrentUserEvent(response));
                clientFactory.getRoleBasedWidgetHandler().handleRoleBasedViews(StartViewImpl.class);
            }
        });
    }

    @Override
    public void scrapeCaseLaw() {
        System.out.println("Scraping case laws");

        CaseLawScraperRequestFactory.CaseLawScraperRequest context = caseLawScraperRequests.caseLawScraperRequest();

        context.scrapeCaseLaws(LawScraperSource.ZIPFILE).fire(new Receiver<ScraperStatusProxy>() {
            @Override
            public void onSuccess(ScraperStatusProxy response) {
                System.out.println("Färdigt");
                System.out.println("Antal lästa rättsfall: " + response.getScrapedLaws());
            }
        });

    }

    @Override
    public void scrapeLaw() {

        System.out.println("Scraping laws");

        LawScraperRequestFactory.LawScraperRequest context = lawScraperRequests.lawScraperRequest();

        context.scrapeLaws(LawScraperSource.ZIPFILE).fire(new Receiver<ScraperStatusProxy>() {
            @Override
            public void onSuccess(ScraperStatusProxy response) {
                System.out.println("Färdigt");
                System.out.println("Antal lästa lagar: " + response.getScrapedLaws());
            }
        });
    }

    /**
     * Navigate to a new Place in the browser
     */

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }

    @Override
    public void searchLaws(final String query) {
        LawRequestFactory.LawRequest context = lawRequests.lawRequest();
        context.findLawByQuery(query).fire(new Receiver<List<LawProxy>>() {

            @Override
            public void onSuccess(List<LawProxy> response) {
                StartView startView = clientFactory.getStartView();
                startView.setLaws(response, query);
            }
        });
    }

    @Override
    public void searchCaseLaws(String query) {
        query = "%" + query + "%";
        CaseLawRequestFactory.CaseLawRequest context = caseLawRequests.caseLawRequest();

        context.findCaseLawByQuery(query).fire(new Receiver<List<CaseLawProxy>>() {
            @Override
            public void onSuccess(List<CaseLawProxy> response) {
                StartView startView = clientFactory.getStartView();
                startView.setCaseLaws(response);
            }
        });
    }

    @Override
    public void getCaseLawsByYearAndCourt(String year, String court) {
        CaseLawRequestFactory.CaseLawRequest context = caseLawRequests.caseLawRequest();
        /*
        context.getCaseLawsByYearAndCourt(year, court).fire(new Receiver<List<DocumentListItem>>() {

            @Override
            public void onSuccess(List<DocumentListItem> response) {
                StartView startView = clientFactory.getStartView();
                startView.setCaseLaws(response);
            }
        });
        */

    }

    @Override
    public void searchLegalResearch(final String query) {
        LegalResearchRequestFactory.LegalResearchRequest context = legalResearchRequests.legalResearchRequest();
        context.findLegalResearchByLoggedInUser().fire(new Receiver<List<LegalResearchProxy>>() {
            @Override
            public void onSuccess(List<LegalResearchProxy> response) {
                StartView startView = clientFactory.getStartView();
                startView.setLegalResearch(response, query);
            }
        });
    }

    @Override
    public void getLegalResearch(final String query) {
        LegalResearchRequestFactory.LegalResearchRequest context = legalResearchRequests.legalResearchRequest();
        context.findLegalResearchByLoggedInUser().fire(new Receiver<List<LegalResearchProxy>>() {
            @Override
            public void onSuccess(List<LegalResearchProxy> response) {
                StartView startView = clientFactory.getStartView();
                startView.setLegalResearch(response, query);
            }
        });
    }

    @Override
    public void addWidgetToTabPanel(Widget widget, String flerpName, String flerpKey) {
        StartView lawView = clientFactory.getStartView();
        //lawView.getDynamicTabPanel().add(widget, flerpName, flerpKey);
    }

    @Override
    public void getDocumentDescription(final DocumentResultElement resultElement) {
        DocumentPartRequestFactory.DocumentRequest context = documentPartRequestFactory.documentRequest();

        context.getDocumentCommentary(resultElement.getDocumentId()).fire(new Receiver<HTMLProxy>() {
            @Override
            public void onSuccess(HTMLProxy response) {
                resultElement.setDescription(response.getHtml());
            }
        });
    }

    @Override
    public void getLegalResearchByLoggedInUser() {
        LegalResearchRequestFactory.LegalResearchRequest context = legalResearchRequests.legalResearchRequest();
        context.findLegalResearchByLoggedInUser().fire(new Receiver<List<LegalResearchProxy>>() {
            @Override
            public void onSuccess(List<LegalResearchProxy> response) {
                StartView startView = clientFactory.getStartView();
                //startView.setLegalResearch(response, query);
            }
        });
    }

    @Override
    public void changeActiveLegalResearch(Long legalResearchId) {
        LegalResearchRequestFactory.LegalResearchRequest context = legalResearchRequests.legalResearchRequest();
        context.setLegalResearchActive(legalResearchId).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                System.out.println("LegalResearch set to active");
                eventBus.fireEvent(new SetCurrentLegalResearchEvent(null));
            }
        });
    }

    @Override
    public void addLegalResearch(final String title, final String description) {
        LegalResearchRequestFactory.LegalResearchRequest context = legalResearchRequests.legalResearchRequest();
        context.addLegalResearch(title, description).fire(new Receiver<Void>() {

            @Override
            public void onSuccess(Void response) {
                System.out.println("Added legal research");
                getLegalResearchByLoggedInUser();
            }

            @Override
            public void onFailure(ServerFailure error) {
                System.out.println(error.getMessage());
            }
        });

    }
}
