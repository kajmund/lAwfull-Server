package lawscraper.client.ui.panels.lawsbynamepanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.client.place.DocumentPlace;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.panels.resulttable.ResultCellTable;
import lawscraper.client.ui.panels.resulttable.ResultCellTableClickEvent;
import lawscraper.client.ui.panels.resulttable.ResultCellTableClickHandler;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanel;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeEvent;
import lawscraper.shared.proxies.LawProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawsByNamePanel extends Composite {
    private static LawResultPanelUiBinder uiBinder = GWT.create(LawResultPanelUiBinder.class);

    private List<LawProxy> laws = new ArrayList<LawProxy>();
    @UiField VerticalTabPanel verticalpanel;
    ResultCellTable resultTable = new ResultCellTable();
    private StartView.Presenter listener;
    private static List<String> alphabet =
            Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                          "T", "U", "V", "X", "Y", "Z", "Å", "Ä", "Ö");

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, LawsByNamePanel> {
    }

    public LawsByNamePanel() {
        initWidget(uiBinder.createAndBindUi(this));
        for (String string : alphabet) {
            verticalpanel.add(new FlowPanel(), string);
        }

        resultTable.addChangeHandler(new ResultCellTableClickHandler() {
            @Override
            public void onResultClick(ResultCellTableClickEvent event) {
                listener.goTo(new DocumentPlace(event.getLawKey()));
            }
        });

    }

    public void setLaws(List<LawProxy> laws, String tabName) {

        this.laws = laws;
        FlowPanel tabContainer =
                (FlowPanel) verticalpanel.getContainerWidget(tabName);

        if (tabContainer.getWidgetIndex(resultTable) == -1) {
            resultTable.setLaws(laws);
            tabContainer.add(resultTable);
        }
    }

    public void setPresenter(StartView.Presenter listener) {
        this.listener = listener;
    }


    @UiHandler("verticalpanel")
    void onVerticalPanelChange(VerticalTabPanelChangeEvent changeEvent) {
        FlowPanel flowPanel = (FlowPanel) changeEvent.getWidget();
        String flerpString = flowPanel.getElement().getInnerText();
        listener.getLawsByAlphabet(flerpString.substring(0, 1), this);
    }
}
