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
import lawscraper.shared.DocumentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class CaseLawCellTable extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private CellTable<DocumentListItem> table;
    private List<DocumentListItem> laws = new ArrayList<DocumentListItem>();
    private CellTableClickHandler handler;

    @UiField FlowPanel lawTableContainer;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, CaseLawCellTable> {
    }

    public CaseLawCellTable() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
        lawTableContainer.add(table);

    }

    private void initPanel() {
        table = new CellTable<DocumentListItem>();
        Column<DocumentListItem, String> lawNameColumn = addLawNameColumn(table);
        table.setColumnWidth(lawNameColumn, 100, Style.Unit.PCT);

    }

    private void clearView() {
        updateLawTableData();
    }

    public void setLawCases(List<DocumentListItem> laws) {
        clearView();
        this.laws = laws;
        updateLawTableData();
    }

    private Column<DocumentListItem, String> addLawNameColumn(CellTable<DocumentListItem> table) {
        Column<DocumentListItem, String> nameColumn = getNameColumn(new ClickableTextCell());
        table.addColumn(nameColumn, "Rättsfall");
        return nameColumn;
    }

    private Column<DocumentListItem, String> getNameColumn(final AbstractCell nameCell) {
        Column<DocumentListItem, String> column = new Column<DocumentListItem, String>(nameCell) {
            @Override
            public String getValue(DocumentListItem object) {
//                return object.getKey().replace("_", " ");
                return object.getTitle().replace("_", " ");
            }
        };

        column.setFieldUpdater(new FieldUpdater<DocumentListItem, String>() {
            @Override
            public void update(int index, DocumentListItem object, String value) {
                handler.onResultClick(new CellTableClickEvent(object.getTitle()));
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
