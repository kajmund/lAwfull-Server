package lawscraper.client.ui.panels.browsepanels;


import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.ui.panels.celltables.CaseLawCellTable;
import lawscraper.client.ui.panels.celltables.CellTableClickEvent;
import lawscraper.client.ui.panels.celltables.CellTableClickHandler;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanel;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeEvent;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeHandler;
import lawscraper.shared.DocumentListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawCasesBrowsePanel extends BrowsePanel {

    private VerticalTabPanel verticalTabPanelCourts;
    private VerticalTabPanel verticalTabPanelYears;
    private String currentYear = null;
    private String currentCourt = null;
    private CaseLawCellTable resultCaseLawTable;


    public void initPanel() {
        super.initPanel();
        verticalTabPanelCourts = getVerticalTabPanel();
        verticalTabPanelYears = new VerticalTabPanel();

        setFlerpNames(Arrays.asList("NJA", "RH", "RK", "MÖD", "MIG", "AD", "MD", "RA", "HFD"));
        setYearFlerps(generateYearFlerps(1981));

        for (String flerpString : getFlerpNames()) {
            verticalTabPanelCourts.add(verticalTabPanelYears, flerpString);
        }

        this.resultCaseLawTable = new CaseLawCellTable();
        this.resultCaseLawTable.addChangeHandler(new CellTableClickHandler() {
            @Override
            public void onResultClick(CellTableClickEvent event) {
                getSearchLawClickHandler().onSearchLawClick(new ResultDocumentClickEvent(event.getDocumentKey()));
            }
        });

        verticalTabPanelYears.addChangeHandler(new VerticalTabPanelChangeHandler() {
            @Override
            public void onVerticalTabPanelChange(VerticalTabPanelChangeEvent event) {
                onVerticalTabPanelChangeAction(event.getWidget().getElement().getInnerText());
            }
        });

        //todo: add changehandlers so a search is performed when a court is clicked and a year flerp is already selected
        //or zero out the choice of a year

        setPanelTitleText("Rättsfall");
        addChangeHandler(new SearchChangeHandler() {
            @Override
            public void onSearchChange(BrowsePanelChangeEvent event) {
                onSearchChangeAction();
            }
        });

        resultCellTableSearchContainer.add(resultCaseLawTable);
    }

    private void onVerticalTabPanelChangeAction(String caseYear) {
        String flerpString = getCurrentCourt() + ":" + caseYear;
        setCurrentYear(caseYear);
        getSearchChangeHandler().onSearchChange(new BrowsePanelChangeEvent(flerpString));
    }

    @Override
    void onSearchChangeAction() {
        String query = searchTextBox.getText();
        if (query.length() > 3) {
            getResultDeckPanel().showWidget(1);
            getSearchChangeHandler().onSearchChange(new BrowsePanelChangeEvent(query, true));
            if (resultCellTableSearchContainer != null && resultCellTableSearchContainer
                    .getWidgetIndex(resultCaseLawTable) == -1) {

            }
        } else {
            if (resultCellTableSearchContainer.getWidgetIndex(resultCaseLawTable) != -1) {
                onVerticalTabPanelChangeAction(getCurrentYear());
            }
            getResultDeckPanel().showWidget(0);
        }
    }

    @Override
    public void verticalPanelChangeAction(String flerpString) {
        setCurrentCourt(flerpString);
        ((FlowPanel) verticalTabPanelCourts.getContainerWidget(flerpString)).add(verticalTabPanelYears);

    }

    private String getCurrentYear() {
        return currentYear;
    }

    private void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    private String getCurrentCourt() {
        return currentCourt;
    }

    private void setCurrentCourt(String currentCourt) {
        this.currentCourt = currentCourt;
    }

    private void setYearFlerps(List<String> caseLawYears) {
        for (String caseLawYear : caseLawYears) {
            verticalTabPanelYears.add(new FlowPanel(), caseLawYear);
        }
    }

    public void setLawCases(List<DocumentListItem> laws) {
        FlowPanel tabContainer =
                (FlowPanel) verticalTabPanelYears.getContainerWidget(getCurrentYear());

        if (searchTextBox.getText().length() <= 3 && tabContainer != null && tabContainer
                .getWidgetIndex(resultCaseLawTable) == -1) {
            tabContainer.add(resultCaseLawTable);
        } else {
            resultCellTableSearchContainer.add(resultCaseLawTable);
        }

        DocumentListItem item = laws.get(0);

        resultCaseLawTable.setLawCases(laws);

    }

    private List<String> generateYearFlerps(int startYear) {
        Date date = new Date();
        DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy");
        List<String> years = new ArrayList<String>();

        int endYear = Integer.decode(dtf.format(date, TimeZone.createTimeZone(0)));
        for (int i = 0; i < endYear - startYear + 1; i++) {
            years.add(String.valueOf(startYear + i));
        }

        return years;
    }
}
