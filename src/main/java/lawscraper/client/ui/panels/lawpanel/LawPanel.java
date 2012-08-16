package lawscraper.client.ui.panels.lawpanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.panels.bookmarkpanel.BookMarkPanel;
import lawscraper.client.ui.panels.boxpanel.BoxPanel;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedFlowPanel;
import lawscraper.client.ui.panels.shortcutpanel.ShortCutPanel;
import lawscraper.client.ui.panels.utilities.ElementWrapper;
import lawscraper.shared.proxies.DocumentBookMarkProxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 2:31 PM
 */
public class LawPanel extends Composite {
    private LawView.Presenter presenter;
    private Set<RoleBasedFlowPanel> roleBasedWidgets = new HashSet<RoleBasedFlowPanel>();
    private Timer timer;

    interface LawPanelUiBinder extends UiBinder<FlowPanel, LawPanel> {
    }

    private static LawPanelUiBinder uiBinder = GWT.create(LawPanelUiBinder.class);
    @UiField HTMLPanel lawContainer;
    @UiField FlowPanel rightWingPanel;
    @UiField ShortCutPanel shortCutPanel;
    @UiField BoxPanel bookMarkBoxPanel;
    @UiField BoxPanel tocBoxPanel;
    @UiField BoxPanel informationBoxPanel;
    @UiField FlowPanel lawPanelContainer;
    @UiField RoleBasedFlowPanel bookMarkBoxPanelContainer;
    @UiField ToggleButton informationButton;
    @UiField ToggleButton tocButton;
    @UiField ToggleButton bookMarkButton;
    @UiField FlowPanel rightMenu;
    @UiField SplitLayoutPanel splitLayoutPanel;
    @UiField ScrollPanel scrollPanel;
    BookMarkPanel bookMarkPanel = new BookMarkPanel();

    private HTMLPanel htmlPanel;


    public LawPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        roleBasedWidgets.add(bookMarkBoxPanelContainer);
        tocBoxPanel.setVisible(false);
        splitLayoutPanel.getElement().getStyle().setPosition(Style.Position.STATIC);
        scrollPanel.ensureDebugId("lawScrollPanel");
        splitLayoutPanel.addHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                Window.alert("resize!");
            }
        }, ResizeEvent.getType());
    }

    public Set<RoleBasedFlowPanel> getRoleBasedWidgets() {
        return roleBasedWidgets;
    }

    public void initScrollHandler() {
        scrollPanel.addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(ScrollEvent event) {
                //Dont scroll until the scrolling has stopped for 100 ms.
                if (timer != null) {
                    timer.cancel();
                }
                // Create a new timer that calls handleOnScroll()
                timer = new Timer() {
                    public void run() {
                        handleOnScroll();
                    }
                };

                // Schedule the timer to run once in 100ms.
                timer.schedule(100);
            }
        });
    }

    private void handleOnScroll() {
        int scrollTop = scrollPanel.getVerticalScrollPosition();

        //The infopanel always on top of screen
        if (scrollTop < lawPanelContainer.getElement().getAbsoluteTop()) {
            shortCutPanel.setVisible(false);
            shortCutPanel.setText("");
            return;
        }

        //todo: add custom tag so you dont have to traverse all the divs
        final NodeList<com.google.gwt.dom.client.Element> elements =
                htmlPanel.getElement().getElementsByTagName("div");

        for (int i = 0; i < elements.getLength(); i++) {
            com.google.gwt.dom.client.Element el = elements.getItem(i);

            int top = el.getOffsetTop();
            int bottom = el.getOffsetHeight() + top;

            if (el.getTitle().length() > 0) {
                if (scrollTop >= top && scrollTop <= bottom) {
                    shortCutPanel.setVisible(true);
                    shortCutPanel.setText(el.getTitle());
                    break;
                }
            }
        }
    }

    public void setLawAsHTML(String html) {
        htmlPanel = new HTMLPanel(html);
        lawContainer.clear();
        lawContainer.add(htmlPanel);
        bookMarkBoxPanel.setContainerWidget(bookMarkPanel);
        initElementClickHandlers();
        rightWingPanel.setVisible(true);
        handleRightMenuVisibility();
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

        ClickHandler tocClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String aHrefString = event.getSource().toString();
                String[] matches = aHrefString.split("\"");
                if (matches.length > 0) {
                    Element el = DOM.getElementById(matches[3].substring(1));
                    int top = el.getOffsetTop();
                    scrollPanel.setVerticalScrollPosition(top);
                }
            }
        };

        //traverse the list to find meta div, then convert it to widget and add it to the boxpanel
        for (int i = 0; i < elements.getLength(); i++) {
            com.google.gwt.dom.client.Element el = elements.getItem(i);
            if (el.getClassName().equals("lawMeta")) {
                informationBoxPanel.setContainerWidget(wrapElementWithWidget(el));
            } else if (el.getClassName().length() > 10 && el.getClassName().substring(0, 10).equals("TOCElement")) {
                NodeList<com.google.gwt.dom.client.Element> tocLinks = el.getElementsByTagName("lawtocitem");
                for (int x = 0; x < tocLinks.getLength(); x++) {
                    com.google.gwt.dom.client.Element tocLinkItem = tocLinks.getItem(x);
                    ElementWrapper tocLinkWidget = wrapElementWithWidget(tocLinkItem);
                    tocLinkWidget.getElement().getStyle().setCursor(Style.Cursor.POINTER);
                    tocLinkWidget.addClickHandler(tocClickHandler);

                }
            }
        }

        ElementWrapper tocWidget = null;

        //traverse the list again to find the toc div, then convert it to widget and add it to the boxpanel
        for (int i = 0; i < elements.getLength(); i++) {
            com.google.gwt.dom.client.Element el = elements.getItem(i);
            tocWidget = wrapElementWithWidget(el);
            if (el.getClassName().equals("lawTableOfContents")) {
                tocBoxPanel.setContainerWidget(tocWidget);
                if (el.getChildCount() > 0) {
                    tocBoxPanel.setVisible(true);
                } else {
                    tocBoxPanel.setVisible(false);
                }
            } else if (bookMarkBoxPanelContainer.isVisible()) {
                addClickHandlerToElement(clickHandler, tocWidget);
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

    @UiHandler("informationButton")
    public void onClickInformationButton(ClickEvent e) {
        if (informationBoxPanel.isVisible()) {
            informationBoxPanel.setVisible(false);
        } else {
            informationBoxPanel.setVisible(true);
        }
        handleRightMenuVisibility();
    }

    @UiHandler("tocButton")
    public void onClickTocButton(ClickEvent e) {
        if (tocBoxPanel.isVisible()) {
            tocBoxPanel.setVisible(false);
        } else {
            tocBoxPanel.setVisible(true);
        }
        handleRightMenuVisibility();
    }

    @UiHandler("bookMarkButton")
    public void onClickBookMarkButton(ClickEvent e) {
        if (bookMarkBoxPanel.isVisible()) {
            bookMarkBoxPanel.setVisible(false);
        } else {
            bookMarkBoxPanel.setVisible(true);
        }
        handleRightMenuVisibility();
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
            lawPanelContainer.getElement().getStyle().setWidth(150, Style.Unit.PCT);
        } else {

            for (BoxPanel boxPanel : boxPanelList) {
                boxPanel.setHeight((totalHeight / boxPanelCount), Style.Unit.PX);
            }

            lawPanelContainer.getElement().getStyle().setWidth(100, Style.Unit.PCT);
        }
    }

    public void setPresenter(LawView.Presenter presenter) {
        this.presenter = presenter;
        bookMarkPanel.setPresenter(presenter);
    }

    public BookMarkPanel getBookMarkPanel() {
        return bookMarkPanel;
    }

    public void setLoading() {
        setLawAsHTML("<b>Hämtar information...</b>");
        rightWingPanel.setVisible(false);
    }

    private List<BoxPanel> getBoxPanelsVisible() {
        List<BoxPanel> boxPanelList = new ArrayList<BoxPanel>();

        if (informationBoxPanel.isVisible()) {
            boxPanelList.add(informationBoxPanel);
        }
        if (tocBoxPanel.isVisible()) {
            boxPanelList.add(tocBoxPanel);
        }
        if (bookMarkBoxPanelContainer.isVisible()) {
            boxPanelList.add(bookMarkBoxPanel);
        }
        return boxPanelList;
    }

}
