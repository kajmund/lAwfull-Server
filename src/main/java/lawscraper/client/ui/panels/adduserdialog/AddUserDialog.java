package lawscraper.client.ui.panels.adduserdialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.client.ui.UserView;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 4/19/12
 * Time: 9:17 AM
 */
public class AddUserDialog extends PopupPanel {
    private static final InfoPopupBox binder = GWT.create(InfoPopupBox.class);
    @UiField com.google.gwt.user.client.ui.Button closeButton;
    @UiField TextBox eMail;
    @UiField TextBox userName;
    @UiField ListBox userRole;
    @UiField TextBox password;
    private UserView.Presenter listener;

    interface InfoPopupBox extends UiBinder<Widget, AddUserDialog> {
    }


    public AddUserDialog(UserView.Presenter listener) {
        super(true);
        setWidget(binder.createAndBindUi(this));
        setGlassEnabled(true);
        center();
        this.listener = listener;
    }

    @UiHandler("closeButton")
    void onClickCloseButton(ClickEvent e) {
        this.hide();
    }

    @UiHandler("saveButton")
    void onClickSaveButton(ClickEvent e) {
        if (eMail.getText().length() > 0 &&
                userName.getText().length() > 0 &&
                password.getText().length() > 0) {
            listener.addUser(eMail.getText(), userName.getText(), password.getText());
            this.hide();
        }
    }
}
