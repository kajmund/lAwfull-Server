package lawscraper.client.ui.panels.lawresultpanel;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.place.LawPlace;
import lawscraper.client.ui.StartView;
import lawscraper.shared.proxies.LawProxy;

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

    private CellTable<LawProxy> table;
    private List<LawProxy> laws = new ArrayList<LawProxy>();
    @UiField FlowPanel lawTableContainer;
    private StartView.Presenter listener;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, LawResultPanel> {
    }

    public LawResultPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        initPanel();
        lawTableContainer.add(table);

    }

    public void initPanel() {
        table = new CellTable<LawProxy>();
        Column<LawProxy, String> lawNameColumn = addLawNameColumn(table);
        table.setColumnWidth(lawNameColumn, 600, Style.Unit.PX);

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
                listener.goTo(new LawPlace(object.getFsNumber()));
            }
        });

        return column;
    }

    public StartView.Presenter getListener() {
        return listener;
    }

    public void setListener(StartView.Presenter listener) {
        this.listener = listener;
    }

    public void updateLawTableData() {
        table.setRowCount(0);
        table.setRowCount(laws.size(), true);
        table.setRowData(laws);
        table.redraw();
    }

}
