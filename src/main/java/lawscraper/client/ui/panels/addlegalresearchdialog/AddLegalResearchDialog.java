package lawscraper.client.ui.panels.addlegalresearchdialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import lawscraper.client.ui.StartView;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 4/19/12
 * Time: 9:17 AM
 */
public class AddLegalResearchDialog extends PopupPanel {
    private static final InfoPopupBox binder = GWT.create(InfoPopupBox.class);
    @UiField Button closeButton;
    @UiField Button saveButton;
    @UiField TextBox name;
    @UiField Label description;
    private StartView.Presenter listener;

    interface InfoPopupBox extends UiBinder<Widget, AddLegalResearchDialog> {
    }


    public AddLegalResearchDialog(StartView.Presenter listener) {
        super(true);
        setWidget(binder.createAndBindUi(this));
        setGlassEnabled(true);
        center();
        this.listener = listener;
    }

    @UiHandler("closeButton")
    public void onClickCloseButton(ClickEvent e) {
        this.hide();
    }

    @UiHandler("saveButton")
    public void onClickSaveButton(ClickEvent e) {
        if (name.getText().trim().length() > 0 && description.getText().trim().length() > 0) {
            listener.addLegalResearch(name.getText(), description.getText());
            this.hide();
        }
    }
}
