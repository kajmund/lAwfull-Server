package lawscraper.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import lawscraper.client.ClientFactory;
import lawscraper.client.activity.GoodbyeActivity;
import lawscraper.client.activity.HelloActivity;
import lawscraper.client.place.GoodbyePlace;
import lawscraper.client.place.HelloPlace;

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
		if (place instanceof HelloPlace) {
            HelloActivity ha = new HelloActivity((HelloPlace) place, clientFactory);
            return ha;
        }
		else if (place instanceof GoodbyePlace)
			return new GoodbyeActivity((GoodbyePlace) place, clientFactory);

		return null;
	}

}
