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
    private String lawId;

    public LawPlace(String token) {
        this.lawId = token;
    }

    public LawPlace(Long id) {
        lawId = Long.toString(id);

    }

    public String getLawId() {
        return lawId;
    }

    public void setLawId(String lawId) {
        this.lawId = lawId;
    }

    public static class Tokenizer implements PlaceTokenizer<LawPlace>
    {

        @Override
        public String getToken(LawPlace place)
        {
            return place.getLawId();
        }

        @Override
        public LawPlace getPlace(String lawId)
        {
            return new LawPlace(lawId);
        }
    }

}
