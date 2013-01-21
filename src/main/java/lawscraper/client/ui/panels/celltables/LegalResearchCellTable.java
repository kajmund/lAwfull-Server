package lawscraper.client.ui.panels.celltables;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.shared.proxies.LegalResearchProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LegalResearchCellTable extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private CellTable<LegalResearchProxy> table;
    private List<LegalResearchProxy> legalResearches = new ArrayList<LegalResearchProxy>();
    private CellTableClickHandler handler;

    @UiField FlowPanel lawTableContainer;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, LegalResearchCellTable> {
    }

    public LegalResearchCellTable() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
        lawTableContainer.add(table);

    }

    private void initPanel() {
        table = new CellTable<LegalResearchProxy>();
        Column<LegalResearchProxy, String> lawNameColumn = addNameColumn(table);
        Column<LegalResearchProxy, Boolean> activeColumn = addActiveColumn(table);
        table.setColumnWidth(lawNameColumn, 95, Style.Unit.PCT);
        table.setColumnWidth(activeColumn, 5, Style.Unit.PCT);

    }

    public void clearView() {
        updateLawTableData();
    }

    public void setLegalResearch(List<LegalResearchProxy> legalResearches) {
        this.legalResearches = legalResearches;
        updateLawTableData();
    }

    private Column<LegalResearchProxy, String> addNameColumn(CellTable<LegalResearchProxy> table) {
        Column<LegalResearchProxy, String> nameColumn = getNameColumn(new ClickableTextCell());
        table.addColumn(nameColumn, "Rättsutredning");
        return nameColumn;
    }

    private Column<LegalResearchProxy, String> getNameColumn(final AbstractCell nameCell) {
        Column<LegalResearchProxy, String> column = new Column<LegalResearchProxy, String>(nameCell) {
            @Override
            public String getValue(LegalResearchProxy object) {
                return object.getTitle();
            }
        };

        column.setFieldUpdater(new FieldUpdater<LegalResearchProxy, String>() {
            @Override
            public void update(int index, LegalResearchProxy object, String value) {
                handler.onResultClick(new CellTableClickEvent(Long.toString(object.getId())));
            }
        });

        return column;
    }

    private Column<LegalResearchProxy, Boolean> addActiveColumn(CellTable<LegalResearchProxy> table) {
        Column<LegalResearchProxy, Boolean> activeColumn = getActiveColumn(new CheckboxCell());
        table.addColumn(activeColumn, "Aktiv");
        return activeColumn;
    }

    private Column<LegalResearchProxy, Boolean> getActiveColumn(final AbstractCell activeCell) {
        return new Column<LegalResearchProxy, Boolean>(activeCell) {
            @Override
            public Boolean getValue(LegalResearchProxy object) {
                return true;
            }
        };
    }


    public void updateLawTableData() {
        table.setRowCount(0);
        table.setRowCount(legalResearches.size(), true);
        table.setRowData(legalResearches);
        table.redraw();
    }

    public HandlerRegistration addChangeHandler(CellTableClickHandler changeHandler) {
        this.handler = changeHandler;
        return this.addHandler(changeHandler, CellTableClickEvent.getType());
    }

}
