package lawscraper.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import lawscraper.client.ui.GoodbyeView;
import lawscraper.client.ui.LawView;
import lawscraper.client.ui.StartView;

public interface ClientFactory
{
	EventBus getEventBus();
	PlaceController getPlaceController();
	StartView getStartView();
	GoodbyeView getGoodbyeView();
    LawView getLawView();
}
