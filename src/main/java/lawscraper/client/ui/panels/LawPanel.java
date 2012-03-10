package lawscraper.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

    public void setLawAsHTML(String html) {
        htmlPanel = new HTMLPanel(html);
        lawContainer.clear();
        lawContainer.add(htmlPanel);
        initElementClickHandlers();
    }

    private void initElementClickHandlers() {
        ClickHandler clickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.stopPropagation();
                Element lawElement = ((ElementWrapper) event.getSource()).getElement();
                if (lawElement.getStyle().getBorderStyle().equals("dotted")) {
                    lawElement.getStyle().setBorderStyle(Style.BorderStyle.NONE);

                } else {
                    lawElement.getStyle().setBorderStyle(Style.BorderStyle.DOTTED);
                    lawElement.getStyle().setBorderColor("red");
                }

            }
        };

        NodeList<com.google.gwt.dom.client.Element> elements = htmlPanel.getElement().getElementsByTagName("div");
        for (int i = 0; i < elements.getLength(); i++) {
            addClickHandlerToElement(elements.getItem(i), clickHandler);
        }

    }

    private void addClickHandlerToElement(com.google.gwt.dom.client.Element element, ClickHandler clickHandler) {
        ElementWrapper elementWrapper = new ElementWrapper(element);
        elementWrapper.addClickHandler(clickHandler);
        elementWrapper.onAttach();
    }

}
