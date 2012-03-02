package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.client.ui.panels.LawPanel;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:56 PM
 */
public class LawViewImpl extends Composite implements LawView {
    private static LawViewImplUiBinder uiBinder = GWT.create(LawViewImplUiBinder.class);
    private Presenter listener;
    @UiField LawPanel lawPanel;

    interface LawViewImplUiBinder extends UiBinder<Widget, LawViewImpl> {
    }

    public LawViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override

    public void setPresenter(Presenter listener) {
        this.listener = listener;
    }

    @Override
    public void setLaw(HTMLProxy law) {
        lawPanel.setLawAsHTML(law.getHtml());

    }
}
