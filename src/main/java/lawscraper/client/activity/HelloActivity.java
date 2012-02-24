package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import lawscraper.client.ClientFactory;
import lawscraper.client.ui.StartView;
import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.proxies.LawProxy;


public class HelloActivity extends AbstractActivity implements StartView.Presenter {
    final LawRequestFactory requests = GWT.create(LawRequestFactory.class);

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */

    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
    private ClientFactory clientFactory;

    public HelloActivity(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }


    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        StartView startView = clientFactory.getHelloView();
        startView.setPresenter(this);
        containerWidget.setWidget(startView.asWidget());
        requests.initialize(eventBus);
    }

    @Override
    public void getLaw() {
        System.out.println("Getting law");
        LawRequestFactory.LawRequest context = requests.lawRequest();

        long id = 0;
        context.find(id).fire(new Receiver<LawProxy>() {
            @Override
            public void onSuccess(LawProxy response) {
                System.out.println("Success!");
                StartView startView = clientFactory.getHelloView();
                startView.setLaw(response);
            }
        });
    }

    @Override
    public void scrapeLaw() {

        System.out.println("Scraping laws");

        LawRequestFactory.LawRequest context = requests.lawRequest();

        /*
        context.scrapeAll().fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void response) {
                System.out.println("Färdigt");
            }
        });
         */
    }

    /**
     * Navigate to a new Place in the browser
     */

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
