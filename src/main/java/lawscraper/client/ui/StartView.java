package lawscraper.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import lawscraper.client.ui.panels.lawsbynamepanel.LawsByNamePanel;
import lawscraper.shared.proxies.LawProxy;
import lawscraper.shared.proxies.LegalResearchProxy;
import lawscraper.shared.proxies.UserProxy;

import java.util.List;

/**
 * View interface. Extends IsWidget so a view impl can easily provide
 * its container widget.
 *
 * @author drfibonacci
 */
public interface StartView extends IsWidget {
    void setName(String helloName);

    void setPresenter(Presenter listener);

    void setLaws(List<LawProxy> law);

    FlowPanel getMainContainer();

    void setUserLoggedIn(UserProxy user);

    void setLegalResearch(List<LegalResearchProxy> response);

    void addFlerpContainer(FlowPanel flerpContainer);

    public interface Presenter {
        void goTo(Place place);

        void scrapeLaw();

        void searchLaws(String query);

        void checkCurrentUser();

        void addLegalResearch(String title, String description);

        void getLegalResearchByLoggedInUser();

        void changeActiveLegalResearch(Long legalResearchId);

        void scrapeCaseLaw();

        void getLawsByAlphabet(String character, LawsByNamePanel lawsByNamePanel);
    }
}