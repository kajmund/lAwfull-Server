package lawscraper.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import lawscraper.shared.proxies.LawProxy;

/**
 * View interface. Extends IsWidget so a view impl can easily provide
 * its container widget.
 *
 * @author drfibonacci
 */
public interface StartView extends IsWidget
{
	void setName(String helloName);
	void setPresenter(Presenter listener);
    void setLaw(LawProxy law);

    void setScrapeUpdate(String message);

    public interface Presenter
	{
		void goTo(Place place);

        void getLaw();

        void scrapeLaw();
    }
}