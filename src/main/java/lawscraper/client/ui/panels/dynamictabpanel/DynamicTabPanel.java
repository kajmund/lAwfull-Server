package lawscraper.client.ui.panels.dynamictabpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeEvent;
import lawscraper.client.ui.panels.verticaltabpanel.VerticalTabPanelChangeHandler;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 7/12/12
 * Time: 3:06 PM
 */
public class DynamicTabPanel extends Composite {

    interface DynamicTabPanelUiBinder extends UiBinder<FlowPanel, DynamicTabPanel> {
    }


    interface Style extends CssResource {
        String register();

        String flerp();

        String mainContainer();

        String flerpSelected();

        String flerpContainer();

        String contentDeckPanel();
    }

    private static DynamicTabPanelUiBinder uiBinder = GWT.create(DynamicTabPanelUiBinder.class);
    @UiField FlowPanel mainContainer;
    @UiField FlowPanel flerpContainer;
    @UiField Style style;
    @UiField DeckPanel tabContentDeckPanel;
    private VerticalTabPanelChangeHandler tabPanelChangeHandler;

    public DynamicTabPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            addClickAction(flerpContainer.getWidget(i));
        }

    }

    public FlowPanel getTabFlerpContainer() {
        return flerpContainer;
    }

    void addClickAction(Widget widget) {
        widget.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                FlowPanel source = (FlowPanel) event.getSource();
                selectFlerp(source);

                if (tabPanelChangeHandler != null) {
                    tabPanelChangeHandler.onVerticalTabPanelChange(new VerticalTabPanelChangeEvent(source));
                }
            }
        }, ClickEvent.getType());
    }

    public boolean selectFlerpIfExists(String flerpName) {
        FlowPanel existingFlerp = getFlerp(flerpName);
        if (existingFlerp != null) {
            selectFlerp(existingFlerp);
            return true;
        }
        return false;
    }

    public void add(Widget content, String flerpName) {

        FlowPanel flerpFlowPanel = new FlowPanel();
        flerpFlowPanel.addStyleName(style.flerp());

        //todo: add style and set it programatically
        FlowPanel xContainer = new FlowPanel();

        Label closeX = new Label("X");
        closeX.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleOnClickFlerp(event);
            }
        });

        xContainer.add(closeX);
        xContainer.getElement().getStyle().setFloat(com.google.gwt.dom.client.Style.Float.RIGHT);
        xContainer.getElement().getStyle().setWidth(25, com.google.gwt.dom.client.Style.Unit.PX);
        xContainer.getElement().getStyle().setFloat(com.google.gwt.dom.client.Style.Float.RIGHT);

        Label flerpNameLabel = new Label(flerpName);
        flerpNameLabel.getElement().getStyle().setFloat(com.google.gwt.dom.client.Style.Float.LEFT);
        flerpFlowPanel.add(flerpNameLabel);
        flerpFlowPanel.add(xContainer);

        addClickAction(flerpFlowPanel);

        flerpContainer.add(flerpFlowPanel);
        tabContentDeckPanel.add(content);
        tabContentDeckPanel.showWidget(tabContentDeckPanel.getWidgetCount() - 1);
        selectFlerp(flerpFlowPanel);
    }

    private FlowPanel getFlerp(String flerpName) {
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            String flerpText = flerpContainer.getWidget(i).getElement().getInnerText().substring(1);
            if (flerpText.contains(flerpName)) {
                return (FlowPanel) flerpContainer.getWidget(i);
            }
        }
        return null;
    }


    public HandlerRegistration addChangeHandler(VerticalTabPanelChangeHandler changeHandler) {
        this.tabPanelChangeHandler = changeHandler;
        return this.addHandler(changeHandler, VerticalTabPanelChangeEvent.getType());
    }

    public DeckPanel getTabContentDeckPanel() {
        return tabContentDeckPanel;
    }

    private void handleOnClickFlerp(ClickEvent event) {
        Label label = (Label) event.getSource();
        FlowPanel tabWidget = (FlowPanel) label.getParent().getParent();
        removeTab(tabWidget);
    }

    private void removeTab(Widget tabWidget) {
        int tabWidgetIndex = ((FlowPanel) tabWidget.getParent()).getWidgetIndex(tabWidget);
        tabContentDeckPanel.remove(tabWidgetIndex);
        tabWidget.removeFromParent();
    }

    private void selectFlerp(FlowPanel source) {
        unSelectAllFlerps();
        FlowPanel parent = (FlowPanel) source.getParent();
        int index = parent.getWidgetIndex(source);

        flerpContainer.remove(source);
        flerpContainer.add(source);
        try {
            Widget widget = tabContentDeckPanel.getWidget(index);
            tabContentDeckPanel.remove(widget);
            tabContentDeckPanel.add(widget);
            tabContentDeckPanel.showWidget(tabContentDeckPanel.getWidgetCount() - 1);

            source.addStyleName(style.flerpSelected());
        } catch (Exception e) {
            System.out.println("selectFlerp::Couldn't get container-widget");
        }
    }

    private void unSelectAllFlerps() {
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            flerpContainer.getWidget(i).removeStyleName(style.flerpSelected());
        }
    }
}
