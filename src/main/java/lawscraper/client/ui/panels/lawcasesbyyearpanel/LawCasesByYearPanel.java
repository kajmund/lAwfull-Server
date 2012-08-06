package lawscraper.client.ui.panels.lawcasesbyyearpanel;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
public class LawCasesByYearPanel extends Composite {
    interface LawCasesByYearPanelUiBinder extends UiBinder<FlowPanel, LawCasesByYearPanel> {
    }

    private static LawCasesByYearPanelUiBinder uiBinder = GWT.create(LawCasesByYearPanelUiBinder.class);

    private List<LawProxy> laws = new ArrayList<LawProxy>();
    @UiField FlowPanel lawTableContainer;

    public LawCasesByYearPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void clearView() {
        this.laws.clear();
    }

    public void setLaws(List<LawProxy> laws) {
        clearView();
        this.laws = laws;
    }

}
