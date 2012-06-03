package lawscraper.client.ui.panels.rolebasedwidgets;

import com.google.gwt.user.client.ui.Composite;
import lawscraper.client.ClientFactory;
import lawscraper.shared.UserRole;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/8/12
 * Time: 9:05 AM
 */
public class RoleBasedComposite extends Composite implements RoleBasedWidget {
    UserRole editRequiresRole;
    UserRole viewRequiresRole;
    private ClientFactory clientFactory;

    protected RoleBasedComposite() {
    }

    protected RoleBasedComposite(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public UserRole getEditRequiresRole() {
        return editRequiresRole;
    }

    public void setEditRequiresRole(UserRole editRequiresRole) {
        this.editRequiresRole = editRequiresRole;
    }

    public UserRole getViewRequiresRole() {
        return viewRequiresRole;
    }

    @Override
    public void setRequiresUserRole(String requiresUserRole) {

    }

    public void setViewRequiresRole(UserRole viewRequiresRole) {
        this.viewRequiresRole = viewRequiresRole;
    }

    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void configureAsNoneVisisble(UserRole userRole) {
        this.setVisible(false);
    }

    @Override
    public void configureAsEditable(UserRole userRole) {
        this.setVisible(true);
    }

    @Override
    public void configureAsViewable(UserRole userRole) {
        this.setVisible(true);
    }

    @Override
    public void setViewRequiresUserRole(String viewRequiresRole) {

    }

    @Override
    public void setEditRequiresUserRole(String editRequiresRole) {

    }
}
