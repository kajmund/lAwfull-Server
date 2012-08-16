package lawscraper.client.ui.panels.shortsearchresultpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import lawscraper.client.ui.panels.resulttable.ResultCellTable;
import lawscraper.client.ui.panels.resulttable.ResultCellTableClickEvent;
import lawscraper.shared.proxies.LawProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class ShortSearchResultPanel extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private CellTable<LawProxy> table;
    private List<LawProxy> laws = new ArrayList<LawProxy>();
    private String lastSearchQuery = "";
    private SearchChangeHandler searchChangeHandler;
    private SearchLawClickHandler searchLawClickHandler;
    private Timer timer;

    @UiField TextBox searchTextBox;
    @UiField ResultCellTable resultCellTable;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, ShortSearchResultPanel> {
    }

    public ShortSearchResultPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setLaws(List<LawProxy> laws) {
        resultCellTable.setLaws(laws);
    }

    @UiHandler("searchTextBox")
    public void onChangeTextBox(KeyUpEvent e) {
        if (searchTextBox.getText().length() > 3) {
            if (timer != null) {
                timer.cancel();
            }
            // Create a new timer that calls the event onSearchChange
            timer = new Timer() {
                public void run() {
                    String query = searchTextBox.getText();
                    if (!lastSearchQuery.equals(query) && query.length() > 3) {
                        searchChangeHandler.onSearchChange(new SearchChangeEvent(query));
                        lastSearchQuery = searchTextBox.getText();
                    }
                }
            };

            // Schedule the timer to run once in 200ms
            timer.schedule(200);
        }
    }

    @UiHandler("resultCellTable")
    void onResultCellTableClick(ResultCellTableClickEvent clickEvent) {
        searchLawClickHandler.onSearchLawClick(new SearchLawClickEvent(clickEvent.getLawKey()));
    }

    public HandlerRegistration addChangeHandler(SearchChangeHandler changeHandler) {
        this.searchChangeHandler = changeHandler;
        return this.addHandler(changeHandler, SearchChangeEvent.getType());
    }

    public HandlerRegistration addChangeHandler(SearchLawClickHandler changeHandler) {
        this.searchLawClickHandler = changeHandler;
        return this.addHandler(changeHandler, SearchLawClickEvent.getType());
    }
}
