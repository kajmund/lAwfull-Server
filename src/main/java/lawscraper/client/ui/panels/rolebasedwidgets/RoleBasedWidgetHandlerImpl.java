package lawscraper.client.ui.panels.rolebasedwidgets;

import lawscraper.shared.proxies.UserProxy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/16/12
 * Time: 7:37 PM
 */
public class RoleBasedWidgetHandlerImpl implements RoleBasedWidgetHandler {
    HashMap<Class<?>, Set<RoleBasedWidget>> roleBasedWidgetMap = new HashMap<Class<?>, Set<RoleBasedWidget>>();

    UserProxy userProxy;

    @Override
    public void handleRoleBasedViews(Class<?> widgetClass) {

        for (RoleBasedWidget roleBasedWidget : roleBasedWidgetMap.get(widgetClass)) {
            if (userProxy == null) {
                roleBasedWidget.configureAsNoneVisisble(null);
                continue;
            }

            if (roleBasedWidget.getEditRequiresRole() != null &&
                    userProxy.getUserRole().ordinal() >= roleBasedWidget.getEditRequiresRole().ordinal()) {
                roleBasedWidget.configureAsEditable(userProxy.getUserRole());
            } else if (roleBasedWidget.getViewRequiresRole() != null && userProxy.getUserRole()
                                                                                 .ordinal() >= roleBasedWidget
                    .getViewRequiresRole().ordinal()) {
                roleBasedWidget.configureAsViewable(userProxy.getUserRole());
            } else {
                roleBasedWidget.configureAsNoneVisisble(userProxy.getUserRole());
            }
        }
    }

    @Override
    public void addRoleBaseWidget(RoleBasedWidget roleBasedWidget, Class<?> parentWidgetClass) {

        if (!roleBasedWidgetMap.containsKey(parentWidgetClass)) {
            Set<RoleBasedWidget> roleBasedWidgets = new HashSet<RoleBasedWidget>();
            roleBasedWidgets.add(roleBasedWidget);
            roleBasedWidgetMap.put(parentWidgetClass, roleBasedWidgets);
        } else {
            Set<RoleBasedWidget> widgetSet = roleBasedWidgetMap.get(parentWidgetClass);
            if (!widgetSet.contains(roleBasedWidget)) {
                widgetSet.add(roleBasedWidget);
            }
        }
    }

    @Override
    public void setUserProxy(UserProxy userProxy) {
        this.userProxy = userProxy;
    }
}
