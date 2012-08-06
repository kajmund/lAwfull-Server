package lawscraper.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:34 PM
 */
public class LawPlace extends Place {

    private String lawKey;

    public LawPlace(String token) {
        this.lawKey = token;
    }

    public String getLawKey() {
        return lawKey;
    }

    public void setLawKey(String lawKey) {
        this.lawKey = lawKey;
    }

    public static class Tokenizer implements PlaceTokenizer<LawPlace> {

        @Override
        public String getToken(LawPlace place) {
            return place.getLawKey();
        }

        @Override
        public LawPlace getPlace(String lawKey) {
            return new LawPlace(lawKey);
        }
    }

}
