package lawscraper.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * View interface. Extends IsWidget so a view impl can easily provide
 * its container widget.
 *
 * @author drfibonacci
 */
public interface LawView extends IsWidget {
     void setPresenter(Presenter listener);

    void setLaw(HTMLProxy law);

    public interface Presenter {
        void goTo(Place place);

        void getLaw();
    }
}