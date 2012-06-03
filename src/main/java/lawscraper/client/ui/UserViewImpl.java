package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import lawscraper.client.ClientFactoryImpl;
import lawscraper.client.ui.panels.userspanel.UsersPanel;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:56 PM
 */
public class UserViewImpl extends Composite implements UserView {
    private static LawViewImplUiBinder uiBinder = GWT.create(LawViewImplUiBinder.class);
    private Presenter listener;
    @UiField UsersPanel usersPanel;

    interface LawViewImplUiBinder extends UiBinder<Widget, UserViewImpl> {
    }

    public UserViewImpl(ClientFactoryImpl clientFactory) {
        super();
        initWidget(uiBinder.createAndBindUi(this));
        //setEditRequiresRole(UserRole.Administrator);
        //setViewRequiresRole(UserRole.Administrator);
        //clientFactory.getRoleBasedWidgetHandler().addRoleBaseWidget(this);
    }

    @Override
    public void setPresenter(Presenter listener) {
        this.listener = listener;
        usersPanel.setListener(listener);
    }

    @Override
    public UsersPanel getUsersPanel() {
        return usersPanel;
    }
}
