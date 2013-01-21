package lawscraper.client.ui;

import com.google.gwt.user.client.ui.Composite;
import lawscraper.client.ClientFactory;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 6/26/12
 * Time: 11:07 PM
 */
public class CaseLawViewImpl extends Composite implements CaseLawView {

    private ClientFactory clientFactory;
    private Presenter presenter;

    public CaseLawViewImpl(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

}
