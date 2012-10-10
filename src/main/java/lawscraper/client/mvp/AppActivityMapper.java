package lawscraper.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import lawscraper.client.ClientFactory;
import lawscraper.client.activity.DocumentViewActivity;
import lawscraper.client.activity.UserViewActivity;
import lawscraper.client.place.DocumentPlace;
import lawscraper.client.place.UserPlace;

public class AppActivityMapper implements ActivityMapper {

    private ClientFactory clientFactory;

    /**
     * AppActivityMapper associates each Place with its corresponding
     * {@link com.google.gwt.activity.shared.Activity}
     *
     * @param clientFactory Factory to be passed to activities
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
        /*
        if (place instanceof StartViewPlace) {
            StartViewActivity startViewActivity = new StartViewActivity((StartViewPlace) place, clientFactory);
            return startViewActivity;
        } else

        */
        if (place instanceof DocumentPlace) {
            DocumentViewActivity lawViewActivity = new DocumentViewActivity((DocumentPlace) place, clientFactory);
            return lawViewActivity;
        } else if (place instanceof UserPlace) {
            UserViewActivity userViewActivity = new UserViewActivity((UserPlace) place, clientFactory);
            return userViewActivity;
        }

        return null;
    }

}
