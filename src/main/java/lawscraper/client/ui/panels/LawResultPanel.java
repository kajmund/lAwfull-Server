package lawscraper.client.ui.panels;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.shared.proxies.LawWrapperProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawResultPanel extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private CellTable<LawWrapperProxy> table;
    private List<LawWrapperProxy> laws = new ArrayList<LawWrapperProxy>();


    interface LawResultPanelUiBinder extends UiBinder<Widget, LawResultPanel> {
    }

    public LawResultPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
    }

    public void initPanel() {
        table = new CellTable<LawWrapperProxy>();
        Column<LawWrapperProxy, String> lawNameColumn = addLawNameColumn(table);
    }

    private Column<LawWrapperProxy, String> addLawNameColumn(CellTable<LawWrapperProxy> table) {
        Column<LawWrapperProxy, String> nameColumn = getNameColumn(new TextCell());
        table.addColumn(nameColumn, "Lagnamn");
        return nameColumn;
    }

    private Column<LawWrapperProxy, String> getNameColumn(final AbstractCell nameCell) {
        return new Column<LawWrapperProxy, String>(nameCell) {
            @Override
            public String getValue(LawWrapperProxy object) {
                return object.getTitle();
            }
        };
    }

    public void updateBaseStationTableData() {
        table.setRowCount(0);
        table.setRowCount(laws.size(), true);
        table.setRowData(laws);
        table.redraw();
    }

}
