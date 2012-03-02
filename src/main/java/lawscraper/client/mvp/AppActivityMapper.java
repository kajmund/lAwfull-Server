package lawscraper.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import lawscraper.client.ClientFactory;
import lawscraper.client.activity.GoodbyeActivity;
import lawscraper.client.activity.LawViewActivity;
import lawscraper.client.activity.StartViewActivity;
import lawscraper.client.place.GoodbyePlace;
import lawscraper.client.place.LawPlace;
import lawscraper.client.place.StartViewPlace;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link com.google.gwt.activity.shared.Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	/**
	 * Map each Place to its corresponding Activity. This would be a great use
	 * for GIN.
	 */
	@Override
	public Activity getActivity(Place place) {
		// This is begging for GIN
		if (place instanceof StartViewPlace) {
            StartViewActivity startViewActivity = new StartViewActivity((StartViewPlace)place, clientFactory);
            return startViewActivity;
        }else if (place instanceof LawPlace) {
            LawViewActivity lawViewActivity = new LawViewActivity((LawPlace)place, clientFactory);
            return lawViewActivity;
        }
		else if (place instanceof GoodbyePlace)
			return new GoodbyeActivity((GoodbyePlace) place, clientFactory);

		return null;
	}

}
