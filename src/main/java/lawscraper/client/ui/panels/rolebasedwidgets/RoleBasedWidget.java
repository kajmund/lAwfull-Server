package lawscraper.client.ui.panels.rolebasedwidgets;

import lawscraper.shared.UserRole;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/16/12
 * Time: 9:25 PM
 */
public interface RoleBasedWidget {

    void configureAsNoneVisisble(UserRole userRole);

    void configureAsEditable(UserRole userRole);

    void configureAsViewable(UserRole userRole);

    void setViewRequiresUserRole(String viewRequiresRole);

    void setEditRequiresUserRole(String editRequiresRole);

    UserRole getEditRequiresRole();

    UserRole getViewRequiresRole();

    void setRequiresUserRole(String requiresUserRole);
}
