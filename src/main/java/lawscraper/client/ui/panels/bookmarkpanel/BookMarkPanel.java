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
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.utils.celltableresources.CellTableResources;
import lawscraper.shared.proxies.DocumentBookMarkProxy;

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

    private List<DocumentBookMarkProxy> bookMarks = new ArrayList<DocumentBookMarkProxy>();
    private CellTable.Resources resources;
    private LawView.Presenter presenter;

    public void setPresenter(LawView.Presenter presenter) {
        this.presenter = presenter;
    }

    interface BookMarkPanellUiBinder extends UiBinder<FlowPanel, BookMarkPanel> {
    }

    private CellTable<DocumentBookMarkProxy> table;
    @UiField FlowPanel bookmarkContainer;

    public BookMarkPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        resources = GWT.create(CellTableResources.class);
        this.initPanel();

    }

    public void initPanel() {
        table = new CellTable<DocumentBookMarkProxy>(0, resources);
        Column<DocumentBookMarkProxy, String> lawNameColumn = addLawNameColumn(table);
        table.setColumnWidth(lawNameColumn, 100, Style.Unit.PCT);
        table.setWidth("100%", true);
        table.addColumnStyleName(0, "tableStyle");
        bookmarkContainer.add(table);

    }

    public void setBookMarks(List<DocumentBookMarkProxy> bookMarks) {
        this.bookMarks = bookMarks;
        updateBookMarkData();
    }

    private Column<DocumentBookMarkProxy, String> addLawNameColumn(CellTable<DocumentBookMarkProxy> table) {
        Column<DocumentBookMarkProxy, String> nameColumn = getNameColumn(new AnchorCell());
        table.addColumn(nameColumn, "");
        return nameColumn;
    }

    private Column<DocumentBookMarkProxy, String> getNameColumn(final AbstractCell nameCell) {
        Column<DocumentBookMarkProxy, String> column = new Column<DocumentBookMarkProxy, String>(nameCell) {
            @Override
            public String getValue(DocumentBookMarkProxy object) {
                return String.valueOf(object.getDocumentId());
            }
        };

        // Add a selection model to handle user selection.
        final SingleSelectionModel<DocumentBookMarkProxy> selectionModel =
                new SingleSelectionModel<DocumentBookMarkProxy>();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            public void onSelectionChange(SelectionChangeEvent event) {
                DocumentBookMarkProxy selected = selectionModel.getSelectedObject();
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
