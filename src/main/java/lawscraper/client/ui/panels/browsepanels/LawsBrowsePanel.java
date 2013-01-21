package lawscraper.client.ui.panels.browsepanels;

import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.ui.panels.celltables.CellTableClickEvent;
import lawscraper.client.ui.panels.celltables.CellTableClickHandler;
import lawscraper.client.ui.panels.celltables.LawCellTable;
import lawscraper.shared.proxies.LawProxy;

import java.util.Arrays;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawsBrowsePanel extends BrowsePanel {
    private LawCellTable lawTableForFlerps;
    private LawCellTable lawTableForSearch;

    @Override
    public void initPanel() {
        super.initPanel();

        lawTableForFlerps = new LawCellTable();
        lawTableForSearch = new LawCellTable();

        setFlerpNames(
                Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                              "S", "T", "U", "V", "X", "Y", "Z", "Å", "Ä", "Ö"));

        for (String string : getFlerpNames()) {
            verticalTabPanel.add(new FlowPanel(), string);
        }

        lawTableForFlerps.addChangeHandler(new CellTableClickHandler() {
            @Override
            public void onResultClick(CellTableClickEvent event) {
                resultTableClickAction(event);
            }
        });

        resultCellTableSearchContainer.add(lawTableForSearch);
        setPanelTitleText("Lagar");
    }

    public void setLaws(List<LawProxy> laws, String searchQuery) {
        if (searchQuery.length() < 3) {
            FlowPanel tabContainer =
                    (FlowPanel) verticalTabPanel.getContainerWidget(searchQuery);

            if (tabContainer.getWidgetIndex(lawTableForFlerps) == -1) {
                lawTableForFlerps.setLaws(laws);
                tabContainer.add(lawTableForFlerps);
            }
        } else {
            lawTableForSearch.setLaws(laws);
        }
    }

    @UiHandler("lawTableForSearch")
    void onResultCellTableClick(CellTableClickEvent clickEvent) {
        resultTableClickAction(clickEvent);
    }

}
