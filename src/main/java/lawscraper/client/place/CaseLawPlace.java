package lawscraper.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/26/12
 * Time: 11:01 PM
 */
public class CaseLawPlace extends Place {
    private String caseLawId;

    public CaseLawPlace(String token) {
        this.caseLawId = token;
    }

    public CaseLawPlace(Long id) {
        caseLawId = Long.toString(id);

    }

    public String getCaseLawId() {
        return caseLawId;
    }

    public void setCaseLawId(String caseLawId) {
        this.caseLawId = caseLawId;
    }

    public static class Tokenizer implements PlaceTokenizer<CaseLawPlace> {

        @Override
        public String getToken(CaseLawPlace place) {
            return place.getCaseLawId();
        }

        @Override
        public CaseLawPlace getPlace(String caseLawId) {
            return new CaseLawPlace(caseLawId);
        }
    }

}
