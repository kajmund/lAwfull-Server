package lawscraper.client.ui.panels.verticaltabpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/12/12
 * Time: 3:06 PM
 */
public class VerticalTabPanel extends Composite {

    interface VerticalTabPanelUiBinder extends UiBinder<FlowPanel, VerticalTabPanel> {
    }


    interface Style extends CssResource {
        String register();

        String flerp();

        String mainContainer();

        String flerpSelected();

        String flerpContainer();
    }

    private static VerticalTabPanelUiBinder uiBinder = GWT.create(VerticalTabPanelUiBinder.class);
    @UiField FlowPanel mainContainer;
    @UiField FlowPanel flerpContainer;
    @UiField Style style;
    @UiField DeckPanel tabContentDeckPanel;
    private VerticalTabPanelChangeHandler tabPanelChangeHandler;


    public VerticalTabPanel() {
        initWidget(uiBinder.createAndBindUi(this));

        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            FlowPanel flowPanel = (FlowPanel) flerpContainer.getWidget(i);
            final String str = flowPanel.getElement().getInnerText();

            addClickAction(flerpContainer.getWidget(i));
        }
    }

    public Widget getContainerWidget(String tabString) {
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            FlowPanel flowPanel = (FlowPanel) flerpContainer.getWidget(i);
            final String str = flowPanel.getElement().getInnerText();
            if (str.equals(tabString)) {
                return tabContentDeckPanel.getWidget(i);
            }
        }
        return null;
    }

    void addClickAction(Widget widget) {
        widget.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                unSelectAllFlerps();
                FlowPanel source = (FlowPanel) event.getSource();
                selectFlerp(source);
                if (tabPanelChangeHandler != null) {
                    tabPanelChangeHandler.onVerticalTabPanelChange(new VerticalTabPanelChangeEvent(source));
                }
            }
        }, ClickEvent.getType());
    }


    /*
     *
     * <g:FlowPanel addStyleNames="{style.flerp}">
     *   <g:Label>A</g:Label>
     * </g:FlowPanel>
     *
     */
    public void add(Widget content, String tabName) {
        FlowPanel flowPanel = new FlowPanel();
        flowPanel.addStyleName(style.flerp());
        flowPanel.add(new Label(tabName));
        flerpContainer.add(flowPanel);

        FlowPanel tabContentFlowPanel = new FlowPanel();
        tabContentFlowPanel.add(content);
        tabContentDeckPanel.add(tabContentFlowPanel);
        addClickAction(flowPanel);
    }


    public HandlerRegistration addChangeHandler(VerticalTabPanelChangeHandler changeHandler) {
        this.tabPanelChangeHandler = changeHandler;
        return this.addHandler(changeHandler, VerticalTabPanelChangeEvent.getType());
    }

    private void selectFlerp(FlowPanel source) {
        FlowPanel parent = (FlowPanel) source.getParent();
        int index = parent.getWidgetIndex(source);
        tabContentDeckPanel.showWidget(index);
        source.addStyleName(style.flerpSelected());
    }

    private void unSelectAllFlerps() {
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            flerpContainer.getWidget(i).removeStyleName(style.flerpSelected());
        }
    }
}
