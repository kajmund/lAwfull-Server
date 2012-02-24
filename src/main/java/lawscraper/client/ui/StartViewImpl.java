package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import lawscraper.shared.proxies.LawWrapperProxy;

import java.util.List;


public class StartViewImpl extends Composite implements StartView {
    private static StartViewImplUiBinder uiBinder = GWT.create(StartViewImplUiBinder.class);

    interface StartViewImplUiBinder extends UiBinder<Widget, StartViewImpl> {
    }

    @UiField Button sendButton;
    @UiField Button scrapeButton;
    @UiField FlowPanel container;
    @UiField Label processingLabel;
    private Presenter listener;
    private String name;

    public StartViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @UiHandler("sendButton")
    void onClickGoodbye(ClickEvent e) {
        listener.getLaw();
    }

    @UiHandler("scrapeButton")
    void onClickScrapeButton(ClickEvent e) {
        listener.scrapeLaw();
    }

    @Override
    public void setPresenter(Presenter listener) {
        this.listener = listener;
    }

    @Override
    public void setLaw(List<LawWrapperProxy> law) {
        container.clear();
        for (LawWrapperProxy lawWrapperProxy : law) {
            container.add(new HTMLPanel(lawWrapperProxy.getTitle()));
        }
    }

    @Override
    public void setScrapeUpdate(String message) {
        String update = processingLabel.getText() + "\n" + message + "\n";
        processingLabel.setText(update + "<br>");
    }
}
