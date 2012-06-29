package lawscraper.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/26/12
 * Time: 9:44 PM
 */
public interface CaseLawView extends IsWidget {
    void setPresenter(Presenter caseLawViewActivity);

    public interface Presenter {
    }
}
