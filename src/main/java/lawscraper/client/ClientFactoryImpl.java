package lawscraper.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import lawscraper.client.ui.*;
import lawscraper.client.ui.panels.rolebasedwidgets.RoleBasedWidgetHandlerImpl;

public class ClientFactoryImpl implements ClientFactory {
    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeController = new PlaceController(eventBus);

    private static final GoodbyeView goodbyeView = new GoodbyeViewImpl();
    private final LawView lawView = new LawViewImpl(this);
    private final UserView userView = new UserViewImpl(this);
    private final StartView startView = new StartViewImpl(this);
    private final CaseLawView caseLawView = new CaseLawViewImpl(this);

    private static final RoleBasedWidgetHandlerImpl roleBasedWidgetHandler = new RoleBasedWidgetHandlerImpl();


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
    public GoodbyeView getGoodbyeView() {
        return goodbyeView;
    }

    @Override
    public LawView getLawView() {
        return lawView;

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
    public RoleBasedWidgetHandlerImpl getRoleBasedWidgetHandler() {
        return roleBasedWidgetHandler;
    }

}
