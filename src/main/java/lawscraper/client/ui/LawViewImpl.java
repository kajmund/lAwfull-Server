package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.client.ClientFactoryImpl;
import lawscraper.client.ui.panels.bookmarkpanel.BookMarkPanel;
import lawscraper.client.ui.panels.dynamictabpanel.DynamicTabPanel;
import lawscraper.client.ui.panels.lawpanel.LawPanel;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:56 PM
 */
public class LawViewImpl extends Composite implements LawView {
    private static LawViewImplUiBinder uiBinder = GWT.create(LawViewImplUiBinder.class);
    private Presenter presenter;
    LawPanel lawPanel = new LawPanel();

    DynamicTabPanel dynamicTabPanel = null;
    @UiField FlowPanel dynamicTabPanelContainer;

    interface LawViewImplUiBinder extends UiBinder<Widget, LawViewImpl> {
    }

    public LawViewImpl(ClientFactoryImpl clientFactory) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLaw(HTMLProxy law) {
        lawPanel = new LawPanel();
        lawPanel.setPresenter(presenter);
        lawPanel.setLawAsHTML(law.getHtml());
        addLawToDynamicTabPanel(law);
        lawPanel.initScrollHandler();
    }

    private void addLawToDynamicTabPanel(HTMLProxy law) {
        if (dynamicTabPanel == null) {
            dynamicTabPanel = new DynamicTabPanel();
            DeckPanel tabContainer = dynamicTabPanel.getTabContentDeckPanel();
            dynamicTabPanelContainer.add(tabContainer);
        }

        if (law.getName().length() > 100) {
            dynamicTabPanel.add(lawPanel, law.getName().substring(0, 100) + "...");
        } else {
            dynamicTabPanel.add(lawPanel, law.getName());
        }
    }

    @Override
    public boolean selectLawIfExists(String lawTitle) {
        if (dynamicTabPanel != null) {
            return dynamicTabPanel.selectFlerpIfExists(lawTitle);
        }
        return false;
    }

    @Override
    public FlowPanel getDynamicFlerpContainer() {
        return dynamicTabPanel.getTabFlerpContainer();
    }


    @Override
    public BookMarkPanel getBookMarkPanel() {
        return lawPanel.getBookMarkPanel();
    }

    @Override
    public LawPanel getLawPanel() {
        return lawPanel;
    }

    @Override
    public void setLoading() {
        lawPanel.setLoading();
    }
}
