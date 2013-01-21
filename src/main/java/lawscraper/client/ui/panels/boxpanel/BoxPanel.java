package lawscraper.client.ui.panels.boxpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 3/15/12
 * Time: 10:20 PM
 */
public class BoxPanel extends Composite {
    private static BoxPanelUiBinder uiBinder = GWT.create(BoxPanelUiBinder.class);
    @UiField FlowPanel boxPanelContainer;
    @UiField Label title;
    @UiField ScrollPanel scrollPanel;

    public void setHeight(double height, Style.Unit px) {
        boxPanelContainer.getElement().getStyle().setHeight(height, px);
        scrollPanel.getElement().getStyle().setHeight(height-30, px);
        this.getElement().getStyle().setHeight(height, px);
    }

    interface BoxPanelUiBinder extends UiBinder<FlowPanel, BoxPanel> {
    }

    public BoxPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setHeaderTitle(String headerTitle) {
        this.title.setText(headerTitle);
    }

    public void setContainerWidget(Widget widget) {
        clearView();
        boxPanelContainer.add(widget);
    }

    public void clearView() {
        boxPanelContainer.clear();
    }

    public void updateScrollPanelHeight(double height) {
        scrollPanel.setHeight(height + "px");
    }
}
