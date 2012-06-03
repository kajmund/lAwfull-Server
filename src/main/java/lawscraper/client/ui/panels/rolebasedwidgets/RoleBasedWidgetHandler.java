package lawscraper.client.ui.panels.rolebasedwidgets;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 5/16/12
 * Time: 7:51 PM
 */
public interface RoleBasedWidgetHandler {

    void handleRoleBasedViews(Class<?> widgetClass);

    void addRoleBaseWidget(RoleBasedWidget roleBasedWidget, Class<?> widgetClass);
}
