package lawscraper.client.ui.panels.documentpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import lawscraper.client.ui.panels.boxpanel.BoxPanel;
import lawscraper.client.ui.panels.customsplitlayoutpanel.CustomSplitLayoutPanel;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedFlowPanel;
import lawscraper.client.ui.panels.utilities.ElementWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class DocumentPanel extends Composite {

    private Set<RoleBasedFlowPanel> roleBasedWidgets = new HashSet<RoleBasedFlowPanel>();

    interface LawPanelUiBinder extends UiBinder<FlowPanel, DocumentPanel> {
    }

    private static LawPanelUiBinder uiBinder = GWT.create(LawPanelUiBinder.class);

    @UiField FlowPanel rightWingPanel;
    @UiField BoxPanel informationBoxPanel;
    @UiField FlowPanel documentPanelContainer;
    @UiField ToggleButton informationButton;
    @UiField FlowPanel rightMenu;
    @UiField(provided = true)
    SplitLayoutPanel splitLayoutPanel = new CustomSplitLayoutPanel();
    @UiField ScrollPanel scrollPanel;
    @UiField FlowPanel lawWidgetContainer;

    public DocumentPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        splitLayoutPanel.getElement().getStyle().setPosition(Style.Position.STATIC);
//        splitLayoutPanel.setWidgetSnapClosedSize(informationButton, 100);
    }

    @UiHandler("informationButton")
    void onClickInformationButton(ClickEvent e) {
        if (informationBoxPanel.isVisible()) {
            informationBoxPanel.setVisible(false);
        } else {
            informationBoxPanel.setVisible(true);
        }
        handleRightMenuVisibility();
    }


    public void setDocumentWidget(Widget widget) {
        lawWidgetContainer.clear();
        lawWidgetContainer.add(widget);
        rightWingPanel.setVisible(true);
        handleRightMenuVisibility();
    }

    private void addClickHandlerToElement(ClickHandler clickHandler, ElementWrapper elementWrapper) {
        elementWrapper.addClickHandler(clickHandler);
    }

    private void handleRightMenuVisibility() {
        double totalHeight = Window.getClientHeight() - 90;
        List<BoxPanel> boxPanelList = getBoxPanelsVisible();
        int boxPanelCount = boxPanelList.size();

        if (boxPanelCount == 0) {
            rightWingPanel.setVisible(false);
        } else {
            rightWingPanel.setVisible(true);
        }

        if (getBoxPanelsVisible().size() == 0) {
            documentPanelContainer.getElement().getStyle().setWidth(150, Style.Unit.PCT);
            ((CustomSplitLayoutPanel) splitLayoutPanel).setSplitPosition(lawWidgetContainer, 0, true);
        } else {

            for (BoxPanel boxPanel : boxPanelList) {
                boxPanel.setHeight((totalHeight / boxPanelCount), Style.Unit.PX);
            }

            documentPanelContainer.getElement().getStyle().setWidth(100, Style.Unit.PCT);
        }
    }

    private List<BoxPanel> getBoxPanelsVisible() {
        List<BoxPanel> boxPanelList = new ArrayList<BoxPanel>();

        if (informationBoxPanel.isVisible()) {
            boxPanelList.add(informationBoxPanel);
        }
        return boxPanelList;
    }

}
