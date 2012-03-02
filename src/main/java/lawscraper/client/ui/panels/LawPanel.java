package lawscraper.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawPanel extends Composite {
    private static LawPanelUiBinder uiBinder = GWT.create(LawPanelUiBinder.class);
    @UiField HTMLPanel lawContainer;
    private HTMLPanel htmlPanel;

    interface LawPanelUiBinder extends UiBinder<FlowPanel, LawPanel> {
    }

    public LawPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setLawAsHTML(String html){
        htmlPanel = new HTMLPanel(html);
        lawContainer.clear();
        lawContainer.add(htmlPanel);
        Element tmp = htmlPanel.getElementById("part_paragraph");


    }

}
