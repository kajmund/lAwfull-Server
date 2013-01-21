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
 * <p/>
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

        String flerpUnSelected();
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

    public boolean selectFlerpIfExists(String key) {
        FlowPanel existingFlerp = getFlerp(key);
        if (existingFlerp != null) {
            selectFlerp(existingFlerp);
            return true;
        }
        return false;
    }

    public void add(Widget content, String flerpName, String flerpKey) {
        //create the flerp
        FlowPanel flerpFlowPanel = createFlerpFlowPanel(flerpName, flerpKey);

        //deside what action if flerp is clicked
        addClickAction(flerpFlowPanel);

        //add it to the upper list
        flerpContainer.add(flerpFlowPanel);

        //add the content, show the widget and set the flerp as selected
        tabContentDeckPanel.add(content);
        tabContentDeckPanel.showWidget(tabContentDeckPanel.getWidgetCount() - 1);
        selectFlerp(flerpFlowPanel);
    }

    private FlowPanel createFlerpFlowPanel(String flerpName, String flerpKey) {
        if (flerpName.length() > 100) {
            flerpName = flerpName.substring(0, 100) + "...";
        }

        FlowPanel flerpFlowPanel = new FlowPanel();
        flerpFlowPanel.addStyleName(style.flerp());
        flerpFlowPanel.getElement().setId(flerpKey);

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
        return flerpFlowPanel;
    }

    private FlowPanel getFlerp(String flerpKey) {
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            //String flerpText = flerpContainer.getWidget(i).getElement().getInnerText().substring(1);
            String widgetFlerpKey = flerpContainer.getWidget(i).getElement().getId();

            if (widgetFlerpKey.equals(flerpKey)) {
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
        removeTab(tabWidget, label);
    }

    private void removeTab(Widget tabWidget, Label label) {
        int tabWidgetIndex = ((FlowPanel) tabWidget.getParent()).getWidgetIndex(tabWidget);
        tabContentDeckPanel.remove(tabWidgetIndex);
        tabWidget.removeFromParent();
        if (tabPanelChangeHandler != null) {
            tabPanelChangeHandler.onVerticalTabPanelChange(new VerticalTabPanelChangeEvent(label));
        }
    }

    private void selectFlerp(FlowPanel source) {
        unSelectAllFlerps();
        FlowPanel parent = (FlowPanel) source.getParent();
        int index = parent.getWidgetIndex(source);

        source.addStyleName(style.flerpSelected());
        source.removeStyleName(style.flerpUnSelected());

        tabContentDeckPanel.showWidget(index);

        /*
        flerpContainer.remove(source);
        flerpContainer.add(source);
        try {
            Widget widget = tabContentDeckPanel.getWidget(index);
            tabContentDeckPanel.remove(widget);
            tabContentDeckPanel.add(widget);
            tabContentDeckPanel.showWidget(tabContentDeckPanel.getWidgetCount() - 1);

            source.addStyleName(style.flerpSelected());
            source.removeStyleName(style.flerpUnSelected());
        } catch (Exception e) {
            System.out.println("selectFlerp::Couldn't get container-widget");
        }
        */
    }

    private void unSelectAllFlerps() {
        for (int i = 0; i < flerpContainer.getWidgetCount(); i++) {
            flerpContainer.getWidget(i).removeStyleName(style.flerpSelected());
            flerpContainer.getWidget(i).addStyleName(style.flerpUnSelected());
        }
    }
}
