package lawscraper.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

//public class StartViewPlace extends ActivityPlace<StartViewActivity>
public class StartViewPlace extends Place
{
	private String token;
	
	public StartViewPlace(String token)
	{
		this.token = token;
	}

	public String getToken()
	{
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<StartViewPlace>
	{

		@Override
		public String getToken(StartViewPlace place)
		{
			return place.getToken();
		}

		@Override
		public StartViewPlace getPlace(String token)
		{
			return new StartViewPlace(token);
		}
	}
}
