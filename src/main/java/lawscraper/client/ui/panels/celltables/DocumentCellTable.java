package lawscraper.client.ui.panels.celltables;

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
import lawscraper.shared.proxies.CaseLawProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class DocumentCellTable extends Composite {
    private static DocumentCellTablePanelUiBinder uiBinder = GWT.create(DocumentCellTablePanelUiBinder.class);

    private CellTable<CaseLawProxy> table;
    private List<CaseLawProxy> laws = new ArrayList<CaseLawProxy>();
    private CellTableClickHandler handler;

    @UiField FlowPanel lawTableContainer;

    interface DocumentCellTablePanelUiBinder extends UiBinder<FlowPanel, DocumentCellTable> {
    }

    public DocumentCellTable() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
        lawTableContainer.add(table);

    }

    public void initPanel() {
        table = new CellTable<CaseLawProxy>();
        Column<CaseLawProxy, String> lawNameColumn = addLawNameColumn(table);
        table.setColumnWidth(lawNameColumn, 100, Style.Unit.PCT);

    }

    public void clearView() {
        updateLawTableData();
    }

    public void setCaseLaws(List<CaseLawProxy> laws) {
        clearView();
        this.laws = laws;
        updateLawTableData();
    }

    private Column<CaseLawProxy, String> addLawNameColumn(CellTable<CaseLawProxy> table) {
        Column<CaseLawProxy, String> nameColumn = getNameColumn(new ClickableTextCell());
        table.addColumn(nameColumn, "Dokument");
        return nameColumn;
    }

    private Column<CaseLawProxy, String> getNameColumn(final AbstractCell nameCell) {
        Column<CaseLawProxy, String> column = new Column<CaseLawProxy, String>(nameCell) {
            @Override
            public String getValue(CaseLawProxy object) {
                return object.getKey().replace("_", " ");
                //return object.getDocumentKey().replace("_", " ");
            }
        };

        column.setFieldUpdater(new FieldUpdater<CaseLawProxy, String>() {
            @Override
            public void update(int index, CaseLawProxy object, String value) {
                handler.onResultClick(new CellTableClickEvent(object.getKey()));
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

    public HandlerRegistration addChangeHandler(CellTableClickHandler changeHandler) {
        this.handler = changeHandler;
        return this.addHandler(changeHandler, CellTableClickEvent.getType());
    }

}
