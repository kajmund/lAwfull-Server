package lawscraper.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.client.ui.panels.documentsearchpanel.DocumentResultElement;
import lawscraper.shared.DocumentListItem;
import lawscraper.shared.proxies.*;

import java.util.List;

/**
 * View interface. Extends IsWidget so a view impl can easily provide
 * its container widget.
 *
 * @author drfibonacci
 */
public interface StartView extends IsWidget {

    void setPresenter(Presenter listener);

    FlowPanel getMainContainer();

    void setUserLoggedIn(UserProxy user);

    void setLegalResearch(List<LegalResearchProxy> response, String query);

    void setLaws(List<LawProxy> laws, String searchQuery);

    boolean selectDocumentTabIfExists(String key);

    void addDocument(HTMLProxy result);

    void setCaseLaws(List<DocumentListItem> response);

    public interface Presenter {
        void goTo(Place place);

        void scrapeLaw();

        void searchLaws(String query);

        void checkCurrentUser();

        void addLegalResearch(String title, String description);

        void getLegalResearchByLoggedInUser();

        void changeActiveLegalResearch(Long legalResearchId);

        void scrapeCaseLaw();

        void searchCaseLaws(String query);

        void getCaseLawsByYearAndCourt(String year, String court);

        void searchLegalResearch(String query);

        void getLegalResearch(String query);

        void addWidgetToTabPanel(Widget widget, String flerpName, String flerpKey);

        void getDocumentDescription(DocumentResultElement resultElement);
    }
}