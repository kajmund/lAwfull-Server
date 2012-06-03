package lawscraper.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import lawscraper.client.ui.panels.bookmarkpanel.BookMarkPanel;
import lawscraper.client.ui.panels.lawpanel.LawPanel;
import lawscraper.shared.proxies.DocumentBookMarkProxy;
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

    BookMarkPanel getBookMarkPanel();

    LawPanel getLawPanel();

    public interface Presenter {
        void goTo(Place place);

        void getLaw();

        DocumentBookMarkProxy createBookMarkRequestProxy();

        void addBookMark(Long lawDocumentPartId);

        void removeBookMark(Long lawDocumentPartId);
    }
}