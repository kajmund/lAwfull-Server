package lawscraper.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import lawscraper.client.ui.GoodbyeView;
import lawscraper.client.ui.GoodbyeViewImpl;
import lawscraper.client.ui.StartView;
import lawscraper.client.ui.StartViewImpl;

public class ClientFactoryImpl implements ClientFactory
{
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final StartView START_VIEW = new StartViewImpl();
	private static final GoodbyeView goodbyeView = new GoodbyeViewImpl();

	@Override
	public EventBus getEventBus()
	{
		return eventBus;
	}

	@Override
	public StartView getHelloView()
	{
		return START_VIEW;
	}

	@Override
	public PlaceController getPlaceController()
	{
		return placeController;
	}

	@Override
	public GoodbyeView getGoodbyeView()
	{
		return goodbyeView;
	}

}
