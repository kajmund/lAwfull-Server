package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import lawscraper.client.ClientFactory;
import lawscraper.client.place.LawPlace;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.StartView;
import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:49 PM
 */
public class LawViewActivity extends AbstractActivity implements LawView.Presenter {
    final LawRequestFactory requests = GWT.create(LawRequestFactory.class);
    private ClientFactory clientFactory;
    private LawView lawView;
    private LawPlace place;

    public LawViewActivity(LawPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;
    }


    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        requests.initialize(eventBus);
        StartView startView = clientFactory.getStartView();
        panel.setWidget(startView.asWidget());

        LawView lawView = clientFactory.getLawView();
        lawView.setPresenter(this);
        this.lawView = lawView;
        startView.getMainContainer().clear();
        startView.getMainContainer().add(lawView);
        getLaw();
    }

    @Override
    public void getLaw(){
        LawRequestFactory.LawRequest context = requests.lawRequest();

        context.findLawHTMLWrapped(Long.valueOf(place.getLawId())).fire(new Receiver<HTMLProxy>() {
            @Override
            public void onSuccess(HTMLProxy result) {
                lawView.setLaw(result);
            }
        });
    }

    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
