package lawscraper.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/26/12
 * Time: 9:34 PM
 */
public class DocumentPlace extends Place {

    private String lawKey;

    public DocumentPlace(String token) {
        this.lawKey = token;
    }

    public String getDocumentKey() {
        return lawKey;
    }

    public void setLawKey(String lawKey) {
        this.lawKey = lawKey;
    }

    public static class Tokenizer implements PlaceTokenizer<DocumentPlace> {

        @Override
        public String getToken(DocumentPlace place) {
            return place.getDocumentKey();
        }

        @Override
        public DocumentPlace getPlace(String lawKey) {
            return new DocumentPlace(lawKey);
        }
    }

}
