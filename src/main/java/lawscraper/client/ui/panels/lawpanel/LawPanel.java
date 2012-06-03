package lawscraper.client.ui.panels.lawpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.panels.bookmarkpanel.BookMarkPanel;
import lawscraper.client.ui.panels.boxpanel.BoxPanel;
import lawscraper.client.ui.panels.shortcutpanel.ShortCutPanel;
import lawscraper.client.ui.panels.utilities.ElementWrapper;
import lawscraper.shared.proxies.DocumentBookMarkProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawPanel extends Composite {
    private LawView.Presenter presenter;

    public void setPresenter(LawView.Presenter presenter) {
        this.presenter = presenter;
        bookMarkPanel.setPresenter(presenter);
    }

    public BookMarkPanel getBookMarkPanel() {
        return bookMarkPanel;

    }


    interface LawPanelUiBinder extends UiBinder<FlowPanel, LawPanel> {
    }

    class ShortCutElement {
        private String text;
        private int top;
        private int bottom;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }
    }

    private static LawPanelUiBinder uiBinder = GWT.create(LawPanelUiBinder.class);
    @UiField HTMLPanel lawContainer;
    @UiField FlowPanel rightWingPanel;
    @UiField ShortCutPanel shortCutPanel;
    @UiField BoxPanel bookMarkBoxPanel;
    @UiField BoxPanel TOCBoxPanel;
    @UiField BoxPanel MetaBoxPanel;
    @UiField FlowPanel lawPanelContainer;
    BookMarkPanel bookMarkPanel = new BookMarkPanel();


    List<ShortCutElement> shortCutElements = new ArrayList<ShortCutElement>();
    private HTMLPanel htmlPanel;


    public LawPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    private void initScrollHandler() {
        final NodeList<com.google.gwt.dom.client.Element> elements =
                htmlPanel.getElement().getElementsByTagName("div");

        shortCutElements.clear();
        for (int i = 0; i < elements.getLength(); i++) {
            com.google.gwt.dom.client.Element el = elements.getItem(i);

            if (el.getTitle().length() > 0) {
                int top = el.getAbsoluteTop();
                int bottom = el.getAbsoluteBottom();
                if (el.getTitle().length() > 0) {
                    ShortCutElement shortCutElement = new ShortCutElement();
                    shortCutElement.setBottom(bottom);
                    shortCutElement.setTop(top);
                    shortCutElement.setText(el.getTitle());
                    shortCutElements.add(shortCutElement);
                }
            }
        }

        Window.addWindowScrollHandler(new Window.ScrollHandler() {
            @Override
            public void onWindowScroll(Window.ScrollEvent event) {
                long scrollTop = Window.getScrollTop();

                //The panel always on top of screen
                if (scrollTop < lawPanelContainer.getElement().getAbsoluteTop()) {
                    shortCutPanel.setVisible(false);
                    shortCutPanel.setText("");
                    rightWingPanel.getElement().getStyle().setPosition(Style.Position.ABSOLUTE);
                    rightWingPanel.getElement().getStyle()
                                  .setTop(lawPanelContainer.getElement().getAbsoluteTop(), Style.Unit.PX);
                    return;
                }
                rightWingPanel.getElement().getStyle().setPosition(Style.Position.FIXED);
                rightWingPanel.getElement().getStyle().setTop(0, Style.Unit.PX);

                for (ShortCutElement shortCutElement : shortCutElements) {
                    int top = shortCutElement.getTop();
                    int bottom = shortCutElement.getBottom();
                    if (scrollTop >= top && scrollTop <= bottom) {
                        shortCutPanel.setVisible(true);
                        shortCutPanel.setText(shortCutElement.getText());
                        break;
                    }
                }
            }

        });
    }

    public void setLawAsHTML(String html) {
        htmlPanel = new HTMLPanel(html);
        lawContainer.clear();
        lawContainer.add(htmlPanel);
        bookMarkBoxPanel.setContainerWidget(bookMarkPanel);
        initElementClickHandlers();
        initScrollHandler();
    }

    public void setBookMarkMarkings(List<DocumentBookMarkProxy> bookMarks) {
        for (DocumentBookMarkProxy bookMark : bookMarks) {
            Element el = htmlPanel.getElementById(bookMark.getDocumentId().toString());
            el.getStyle().setBorderStyle(Style.BorderStyle.DOTTED);
            el.getStyle().setBorderColor("red");

        }

    }

    private void initElementClickHandlers() {
        ClickHandler clickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.stopPropagation();
                Element lawElement = ((ElementWrapper) event.getSource()).getElement();
                String id = lawElement.getId();

                if (lawElement.getStyle().getBorderStyle().equals("dotted")) {
                    lawElement.getStyle().setBorderStyle(Style.BorderStyle.NONE);
                    if (!id.equals("")) {
                        presenter.removeBookMark(Long.decode(id));
                    }

                } else {
                    lawElement.getStyle().setBorderStyle(Style.BorderStyle.DOTTED);
                    lawElement.getStyle().setBorderColor("red");

                    if (!id.equals("")) {
                        presenter.addBookMark(Long.decode(id));
                    }
                }

                bookMarkPanel.updateBookMarkData();
            }
        };

        NodeList<com.google.gwt.dom.client.Element> elements = htmlPanel.getElement().getElementsByTagName("div");

        //traverse the list to find meta div, then convert it to widget and add it to the boxpanel
        for (int i = 0; i < elements.getLength(); i++) {
            com.google.gwt.dom.client.Element el = elements.getItem(i);
            if (el.getClassName().equals("lawMeta")) {
                MetaBoxPanel.setContainerWidget(wrapElementWithWidget(el));
            }
        }

        //traverse the list again to find the toc div, then convert it to widget and add it to the boxpanel
        for (int i = 0; i < elements.getLength(); i++) {
            com.google.gwt.dom.client.Element el = elements.getItem(i);
            ElementWrapper elementWrapper = wrapElementWithWidget(el);
            if (el.getClassName().equals("lawTableOfContents")) {
                TOCBoxPanel.setContainerWidget(elementWrapper);
            } else {
                addClickHandlerToElement(clickHandler, elementWrapper);
            }
        }

    }

    private ElementWrapper wrapElementWithWidget(com.google.gwt.dom.client.Element element) {
        ElementWrapper elementWrapper = new ElementWrapper(element);
        elementWrapper.onAttach();
        return elementWrapper;
    }

    private void addClickHandlerToElement(ClickHandler clickHandler, ElementWrapper elementWrapper) {
        elementWrapper.addClickHandler(clickHandler);
    }

}
