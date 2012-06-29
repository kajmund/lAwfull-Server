package lawscraper.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import lawscraper.client.ClientFactory;
import lawscraper.client.place.CaseLawPlace;
import lawscraper.client.ui.CaseLawView;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/26/12
 * Time: 9:43 PM
 */
public class CaseLawViewActivity extends AbstractActivity implements CaseLawView.Presenter {
    private ClientFactory clientFactory;
    private CaseLawPlace place;
    private CaseLawView caseLawView;

    public CaseLawViewActivity(CaseLawPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.place = place;

        CaseLawView caseLawView = clientFactory.getCaseLawView();
        caseLawView.setPresenter(this);
        this.caseLawView = caseLawView;

    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {

    }
}
