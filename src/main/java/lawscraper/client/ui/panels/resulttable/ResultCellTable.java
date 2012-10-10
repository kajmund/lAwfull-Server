package lawscraper.client.ui.panels.resulttable;

import com.google.gwt.cell.client.AbstractCell;
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
import lawscraper.shared.proxies.LawProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class ResultCellTable extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private CellTable<LawProxy> table;
    private List<LawProxy> laws = new ArrayList<LawProxy>();
    private ResultCellTableClickHandler handler;

    @UiField FlowPanel lawTableContainer;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, ResultCellTable> {
    }

    public ResultCellTable() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
        lawTableContainer.add(table);

    }

    public void initPanel() {
        table = new CellTable<LawProxy>();
        Column<LawProxy, String> lawNameColumn = addLawNameColumn(table);
        table.setColumnWidth(lawNameColumn, 100, Style.Unit.PCT);

    }

    public void clearView() {
        this.laws.clear();
        updateLawTableData();
    }

    public void setLaws(List<LawProxy> laws) {
        clearView();
        this.laws = laws;
        updateLawTableData();
    }

    private Column<LawProxy, String> addLawNameColumn(CellTable<LawProxy> table) {
        Column<LawProxy, String> nameColumn = getNameColumn(new ClickableTextCell());
        table.addColumn(nameColumn, "Lagnamn");
        return nameColumn;
    }

    private Column<LawProxy, String> getNameColumn(final AbstractCell nameCell) {
        Column<LawProxy, String> column = new Column<LawProxy, String>(nameCell) {
            @Override
            public String getValue(LawProxy object) {
                return object.getTitle();
            }
        };

        column.setFieldUpdater(new FieldUpdater<LawProxy, String>() {
            @Override
            public void update(int index, LawProxy object, String value) {
                handler.onResultClick(new ResultCellTableClickEvent(object.getDocumentKey()));
            }
        });

        return column;
    }

    public void updateLawTableData() {
        table.setRowCount(0);
        table.setRowCount(laws.size(), true);
        table.setRowData(laws);
        table.redraw();
    }

    public HandlerRegistration addChangeHandler(ResultCellTableClickHandler changeHandler) {
        this.handler = changeHandler;
        return this.addHandler(changeHandler, ResultCellTableClickEvent.getType());
    }

}
