package lawscraper.client.ui.panels.addlegalresearchdialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 4/19/12
 * Time: 9:17 AM
 */
public class AddLegalResearchDialog extends PopupPanel implements HasClickHandlers {
    private static final InfoPopupBox binder = GWT.create(InfoPopupBox.class);
    @UiField Button closeButton;
    @UiField Button saveButton;
    @UiField TextBox name;
    @UiField TextBox description;

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addHandler(handler, ClickEvent.getType());
    }

    interface InfoPopupBox extends UiBinder<Widget, AddLegalResearchDialog> {
    }


    public AddLegalResearchDialog() {
        super(true);
        setWidget(binder.createAndBindUi(this));
        setGlassEnabled(true);
        center();
    }

    @UiHandler("closeButton")
    void onClickCloseButton(ClickEvent e) {
        this.hide();
    }


    @UiHandler("saveButton")
    void onClickSaveButton(ClickEvent e) {
        if (name.getText().trim().length() > 0 && description.getText().trim().length() > 0) {
            this.fireEvent(e);
            this.hide();
        }
    }

    public TextBox getName() {
        return name;
    }

    public TextBox getDescription() {
        return description;
    }
}
