package lawscraper.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import lawscraper.client.ui.GoodbyeView;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.UserView;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedWidgetHandlerImpl;

public interface ClientFactory
{
    RoleBasedWidgetHandlerImpl getRoleBasedWidgetHandler();

    EventBus getEventBus();
	PlaceController getPlaceController();
	StartView getStartView();
	GoodbyeView getGoodbyeView();
    LawView getLawView();
    UserView getUserView();
}
