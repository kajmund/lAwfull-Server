package lawscraper.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/25/12
 * Time: 9:53 AM
 */
public class UserPlace extends Place {
    private String token;

    public UserPlace(String token)
    {
        this.token = token;
    }

    public UserPlace() {

    }

    public String getToken()
    {
        return token;
    }

    public static class Tokenizer implements PlaceTokenizer<UserPlace>
    {
        @Override
        public String getToken(UserPlace place)
        {
            return place.getToken();
        }

        @Override
        public UserPlace getPlace(String token)
        {
            return new UserPlace(token);
        }
    }
}

