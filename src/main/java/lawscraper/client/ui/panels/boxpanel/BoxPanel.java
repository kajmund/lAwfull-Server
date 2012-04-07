package lawscraper.client.ui.panels.boxpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 3/15/12
 * Time: 10:20 PM
 */
public class BoxPanel extends Composite {
    private static BoxPanelUiBinder uiBinder = GWT.create(BoxPanelUiBinder.class);
    @UiField FlowPanel boxPanelContainer;
    @UiField Label title;

    interface BoxPanelUiBinder extends UiBinder<FlowPanel, BoxPanel> {
    }

    public BoxPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setHeaderTitle(String headerTitle){
        this.title.setText(headerTitle);
    }
    
    public void setContainerWidget(Widget widget) {
        clearView();
        boxPanelContainer.add(widget);
    }

    public void clearView() {
        boxPanelContainer.clear();
    }
}
