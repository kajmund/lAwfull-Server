package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import lawscraper.client.ClientFactory;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.StartViewImpl;
import lawscraper.client.ui.events.*;
import lawscraper.client.ui.panels.lawsbynamepanel.LawsByNamePanel;
import lawscraper.shared.*;
import lawscraper.shared.proxies.LawProxy;
import lawscraper.shared.proxies.LegalResearchProxy;
import lawscraper.shared.proxies.ScraperStatusProxy;
import lawscraper.shared.proxies.UserProxy;
import lawscraper.shared.scraper.LawScraperSource;

import java.util.List;


public class StartViewActivity extends AbstractActivity implements StartView.Presenter {
    final LawRequestFactory requests = GWT.create(LawRequestFactory.class);
    final LawScraperRequestFactory lawScraperRequests = GWT.create(LawScraperRequestFactory.class);

    final CaseLawScraperRequestFactory caseLawScraperRequests = GWT.create(CaseLawScraperRequestFactory.class);

    final UserRequestFactory userRequests = GWT.create(UserRequestFactory.class);
    final LegalResearchRequestFactory legalResearchRequests = GWT.create(LegalResearchRequestFactory.class);

    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
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
        requests.initialize(eventBus);
        lawScraperRequests.initialize(eventBus);
        caseLawScraperRequests.initialize(eventBus);
        userRequests.initialize(eventBus);
        legalResearchRequests.initialize(eventBus);

        this.eventBus = eventBus;
        subscribeToChangeUserEvent(eventBus);
        subscribeToChangeLawEvent(eventBus);
        startView.setPresenter(this);
        getLegalResearchByLoggedInUser();
        clientFactory.getRoleBasedWidgetHandler().handleRoleBasedViews(StartViewImpl.class);
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
                if (flerpContainer == null) {
                    flerpContainer = event.getFlerpContainer();
                    clientFactory.getStartView().addFlerpContainer(flerpContainer);
                }
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
    public void getLawsByAlphabet(String query, final LawsByNamePanel lawsByNamePanel) {
        query += "*";

        LawRequestFactory.LawRequest context = requests.lawRequest();
        context.findLawByQuery(query).fire(new Receiver<List<LawProxy>>() {

            @Override
            public void onSuccess(List<LawProxy> response) {
                lawsByNamePanel.setLaws(response);
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
    public void searchLaws(String query) {
        LawRequestFactory.LawRequest context = requests.lawRequest();
        context.findLawByQuery(query).fire(new Receiver<List<LawProxy>>() {

            @Override
            public void onSuccess(List<LawProxy> response) {
                StartView startView = clientFactory.getStartView();
                startView.setLaws(response);
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
                startView.setLegalResearch(response);
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
