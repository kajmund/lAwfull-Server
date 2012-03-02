package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import lawscraper.client.ClientFactory;
import lawscraper.client.place.StartViewPlace;
import lawscraper.client.ui.StartView;
import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.LawScraperRequestFactory;
import lawscraper.shared.proxies.LawProxy;
import lawscraper.shared.proxies.ScraperStatusProxy;
import lawscraper.shared.scraper.LawScraperSource;

import java.util.List;


public class StartViewActivity extends AbstractActivity implements StartView.Presenter {
    final LawRequestFactory requests = GWT.create(LawRequestFactory.class);
    final LawScraperRequestFactory scraperRequests = GWT.create(LawScraperRequestFactory.class);

    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
    private ClientFactory clientFactory;

    public StartViewActivity(StartViewPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }


    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        StartView startView = clientFactory.getStartView();
        startView.setPresenter(this);
        containerWidget.setWidget(startView.asWidget());
        requests.initialize(eventBus);
        scraperRequests.initialize(eventBus);
    }

    @Override
    public void getLaw() {
        System.out.println("Getting law");
        LawRequestFactory.LawRequest context = requests.lawRequest();

        context.findAll().fire(new Receiver<List<LawProxy>>() {
            @Override
            public void onSuccess(List<LawProxy> response) {
                System.out.println("Success!");
                StartView startView = clientFactory.getStartView();
                startView.setLaws(response);
            }
        });
    }

    @Override
    public void scrapeLaw() {

        System.out.println("Scraping laws");

        LawScraperRequestFactory.LawScraperRequest context = scraperRequests.lawScraperRequest();

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
}
