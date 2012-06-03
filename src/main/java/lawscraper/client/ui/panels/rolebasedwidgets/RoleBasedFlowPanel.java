package lawscraper.client.ui.panels.rolebasedwidgets;

import com.google.gwt.user.client.ui.FlowPanel;
import lawscraper.shared.UserRole;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/17/12
 * Time: 10:46 AM
 */
public class RoleBasedFlowPanel extends FlowPanel implements RoleBasedWidget {
    private UserRole viewRequiresRole;
    private UserRole editRequiresRole;

    public void setViewRequiresUserRole(UserRole viewRequiresRole) {
        this.viewRequiresRole = viewRequiresRole;
    }

    public void setEditRequiresUserRole(UserRole editRequiresRole) {
        this.editRequiresRole = editRequiresRole;
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
    public UserRole getEditRequiresRole() {
        return editRequiresRole;
    }

    @Override
    public UserRole getViewRequiresRole() {
        return viewRequiresRole;
    }


    //uibinder specifics
    @Override
    public void setViewRequiresUserRole(String viewRequiresUserRole) {
        setViewRequiresUserRole(UserRole.getVal(viewRequiresUserRole));
    }

    @Override
    public void setEditRequiresUserRole(String editRequiresUserRole) {
        setEditRequiresUserRole(UserRole.getVal(editRequiresUserRole));
    }

    @Override
    public void setRequiresUserRole(String requiresUserRole) {
        this.setEditRequiresUserRole(requiresUserRole);
        this.setViewRequiresUserRole(requiresUserRole);
    }
}
