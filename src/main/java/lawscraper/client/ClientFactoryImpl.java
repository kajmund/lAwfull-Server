package lawscraper.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import lawscraper.client.ui.*;
import lawscraper.client.ui.clientcache.ClientCache;
import lawscraper.client.ui.clientcache.ClientCacheImpl;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedWidgetHandler;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedWidgetHandlerImpl;

public class ClientFactoryImpl implements ClientFactory {
    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);

    private final UserView userView = new UserViewImpl(this);
    private final StartView startView = new StartViewImpl(this);
    private final CaseLawView caseLawView = new CaseLawViewImpl(this);

    private static final RoleBasedWidgetHandler roleBasedWidgetHandler = new RoleBasedWidgetHandlerImpl();

    private static final ClientCache clientCache = new ClientCacheImpl();

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public StartView getStartView() {
        return startView;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public CaseLawView getCaseLawView() {
        return caseLawView;
    }

    @Override
    public UserView getUserView() {
        return userView;
    }

    @Override
    public RoleBasedWidgetHandler getRoleBasedWidgetHandler() {
        return roleBasedWidgetHandler;
    }

    @Override
    public ClientCache getClientCache() {
        return clientCache;
    }

}
