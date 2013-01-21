package lawscraper.client.ui.panels.browsepanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import lawscraper.client.ui.panels.celltables.CellTableClickEvent;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanel;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeEvent;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 11/9/12
 * Time: 12:21 PM
 */
public abstract class BrowsePanel extends Composite {
    private String lastSearchQuery = "";
    private static List<String> flerps;
    private SearchChangeHandler searchChangeHandler;
    private SearchLawClickHandler searchLawClickHandler;
    private Timer timer;


    @UiField VerticalTabPanel verticalTabPanel;
    @UiField TextBox searchTextBox;
    @UiField DeckPanel resultDeckPanel;
    @UiField Label panelTitle;
    @UiField FlowPanel resultCellTableSearchContainer;


    private static BrowsePanelUiBinder uiBinder = GWT.create(BrowsePanelUiBinder.class);

    interface BrowsePanelUiBinder extends UiBinder<FlowPanel, BrowsePanel> {
    }


    @UiHandler("searchTextBox")
    public void onChangeTextBox(KeyUpEvent e) {
        onSearchChangeAction();
    }

    public BrowsePanel() {
        initPanel();
    }

    void initWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    void initPanel() {
        initWidget();
        resultDeckPanel.showWidget(0);
    }

    public void setPanelTitleText(String panelTitleText) {
        this.panelTitle.setText(panelTitleText);
    }

    void onSearchChangeAction() {
        if (searchTextBox.getText().length() > 3) {
            resultDeckPanel.showWidget(1);
            if (timer != null) {
                timer.cancel();
            }
            // Create a new timer that calls the event onSearchChange
            timer = new Timer() {
                public void run() {
                    String query = searchTextBox.getText();
                    if (!getLastSearchQuery().equals(query) && query.length() > 3) {
                        getSearchChangeHandler().onSearchChange(new BrowsePanelChangeEvent(query, true));
                        setLastSearchQuery(query);
                    }
                }
            };

            // Schedule the timer to run once in 200ms
            timer.schedule(200);
        } else {
            resultDeckPanel.showWidget(0);
        }
    }

    public String getLastSearchQuery() {
        return lastSearchQuery;
    }

    public void setLastSearchQuery(String lastSearchQuery) {
        this.lastSearchQuery = lastSearchQuery;
    }

    public HandlerRegistration addChangeHandler(SearchChangeHandler changeHandler) {
        this.searchChangeHandler = changeHandler;
        return this.addHandler(changeHandler, BrowsePanelChangeEvent.getType());
    }

    public HandlerRegistration addChangeHandler(SearchLawClickHandler changeHandler) {
        this.searchLawClickHandler = changeHandler;
        return this.addHandler(changeHandler, ResultDocumentClickEvent.getType());
    }

    public SearchChangeHandler getSearchChangeHandler() {
        return searchChangeHandler;
    }

    public void setSearchChangeHandler(SearchChangeHandler searchChangeHandler) {
        this.searchChangeHandler = searchChangeHandler;
    }

    public SearchLawClickHandler getSearchLawClickHandler() {
        return searchLawClickHandler;
    }

    public void setSearchLawClickHandler(SearchLawClickHandler searchLawClickHandler) {
        this.searchLawClickHandler = searchLawClickHandler;
    }

    public VerticalTabPanel getVerticalTabPanel() {
        return verticalTabPanel;
    }

    DeckPanel getResultDeckPanel() {
        return resultDeckPanel;
    }

    static List<String> getFlerpNames() {
        return flerps;
    }

    void setFlerpNames(List<String> flerps) {
        this.flerps = flerps;
    }

    @UiHandler("verticalTabPanel")
    void onVerticalPanelChange(VerticalTabPanelChangeEvent changeEvent) {
        FlowPanel flowPanel = (FlowPanel) changeEvent.getWidget();
        String flerpString = flowPanel.getElement().getInnerText();
        verticalPanelChangeAction(flerpString);

    }

    void verticalPanelChangeAction(String flerpString) {
        getSearchChangeHandler().onSearchChange(new BrowsePanelChangeEvent(flerpString));
    }

    void resultTableClickAction(CellTableClickEvent clickEvent) {
        getSearchLawClickHandler().onSearchLawClick(new ResultDocumentClickEvent(clickEvent.getDocumentKey()));
    }
}
