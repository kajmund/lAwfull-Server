package lawscraper.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import lawscraper.client.ui.*;
import lawscraper.client.ui.clientcache.ClientCache;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedWidgetHandler;

public interface ClientFactory {
    ClientCache getClientCache();

    RoleBasedWidgetHandler getRoleBasedWidgetHandler();

    EventBus getEventBus();

    PlaceController getPlaceController();

    StartView getStartView();

    CaseLawView getCaseLawView();

    UserView getUserView();
}
