package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.client.ClientFactoryImpl;
import lawscraper.client.ui.panels.bookmarkpanel.BookMarkPanel;
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
    @UiField LawPanel lawPanel;

    interface LawViewImplUiBinder extends UiBinder<Widget, LawViewImpl> {
    }

    public LawViewImpl(ClientFactoryImpl clientFactory) {
        initWidget(uiBinder.createAndBindUi(this));

    }

    @Override

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        lawPanel.setPresenter(presenter);
    }

    @Override
    public void setLaw(HTMLProxy law) {
        lawPanel.setLawAsHTML(law.getHtml());

    }

    @Override
    public BookMarkPanel getBookMarkPanel() {
        return lawPanel.getBookMarkPanel();
    }

    @Override
    public LawPanel getLawPanel() {
        return lawPanel;
    }
}
