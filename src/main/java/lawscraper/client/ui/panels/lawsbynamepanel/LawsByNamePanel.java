package lawscraper.client.ui.panels.lawsbynamepanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanel;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeEvent;
import lawscraper.shared.proxies.LawProxy;

import java.util.ArrayList;
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
    @UiField FlowPanel lawTableContainer;
    @UiField VerticalTabPanel verticalpanel;
    private StartView.Presenter listener;

    interface LawResultPanelUiBinder extends UiBinder<FlowPanel, LawsByNamePanel> {
    }

    public LawsByNamePanel() {
        initWidget(uiBinder.createAndBindUi(this));
        verticalpanel.add(new Label("A"), "A");
        verticalpanel.add(new Label("B"), "B");
        verticalpanel.add(new Label("C"), "C");
        verticalpanel.add(new Label("D"), "D");
        verticalpanel.add(new Label("E"), "E");
    }

    public void clearView() {
        this.laws.clear();
    }

    public void setLaws(List<LawProxy> laws) {
        clearView();
        this.laws = laws;
        FlowPanel tabContainer = (FlowPanel) verticalpanel.getContainerWidget("A");
        int widgetIndex = ((FlowPanel) tabContainer.getParent()).getWidgetIndex(tabContainer);

        for (LawProxy law : laws) {
            tabContainer.add(new Label(law.getTitle()));
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
        Window.alert(flowPanel.getElement().getInnerText());
    }
}
