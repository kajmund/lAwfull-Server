package lawscraper.client.ui.panels.bookmarkpanel;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import lawscraper.client.ui.utils.celltableresources.CellTableResources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class BookMarkPanel extends Composite {
    private static BookMarkPanellUiBinder uiBinder = GWT.create(BookMarkPanellUiBinder.class);

    private List<BookMark> bookMarks = new ArrayList<BookMark>();
    private CellTable.Resources resources;

    interface BookMarkPanellUiBinder extends UiBinder<FlowPanel, BookMarkPanel> {
    }

    private CellTable<BookMark> table;
    @UiField FlowPanel bookmarkContainer;

    public BookMarkPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        resources = GWT.create(CellTableResources.class);
        this.initPanel();

    }

    public void initPanel() {
        table = new CellTable<BookMark>(0, resources);
        Column<BookMark, String> lawNameColumn = addLawNameColumn(table);
        table.setColumnWidth(lawNameColumn, 100, Style.Unit.PCT);
        table.setWidth("100%", true);
        table.addColumnStyleName(0, "tableStyle");
        bookmarkContainer.add(table);

    }

    public void clearView() {
        this.bookMarks.clear();
        updateBookMarkData();
    }


    public void setBookMarks(List<BookMark> bookMarks) {
        this.bookMarks = bookMarks;
    }

    public List<BookMark> getBookMarks() {
        return bookMarks;
    }

    private Column<BookMark, String> addLawNameColumn(CellTable<BookMark> table) {
        Column<BookMark, String> nameColumn = getNameColumn(new AnchorCell());
        table.addColumn(nameColumn, "");
        return nameColumn;
    }

    private Column<BookMark, String> getNameColumn(final AbstractCell nameCell) {
        Column<BookMark, String> column = new Column<BookMark, String>(nameCell) {
            @Override
            public String getValue(BookMark object) {
                return String.valueOf(object.getId());
            }
        };

        // Add a selection model to handle user selection.
        final SingleSelectionModel<BookMark> selectionModel = new SingleSelectionModel<BookMark>();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                BookMark selected = selectionModel.getSelectedObject();
                if (selected != null) {

                }
            }
        });

        return column;
    }

    public void updateBookMarkData() {
        table.setRowCount(0);
        table.setRowCount(bookMarks.size(), true);
        table.setRowData(bookMarks);
        table.redraw();
    }
}
