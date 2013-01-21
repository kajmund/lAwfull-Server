package lawscraper.server.scrapers.caselawscraper;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/10/12
 * Time: 8:40 AM
 */

public class RegularExpressionTest {

    @Test
    public void testUrlString() {
        List<String> urlStrings = Arrays.asList("http://rinfo.lagrummet.se/publ/rattsfall/ad/1977:124",
                                                "http://rinfo.lagrummet.se/publ/rattsfall/nja/1988s503");
        String documentId = "";

        for (String urlString : urlStrings) {
            RegExp regExp = RegExp.compile("http://rinfo.lagrummet.se/publ/rattsfall/(.*)/(\\d\\d\\d\\d)(:(.*)|s(.*))");
            MatchResult matcher = regExp.exec(urlString);
            boolean matchFound = (matcher != null); // equivalent to regExp.test(inputStr);

            if (matchFound) {
                // Get all groups for this match
                String cInstance = matcher.getGroup(1).toUpperCase();
                String year = matcher.getGroup(2);
                String page = matcher.getGroup(3).substring(1);

                if (cInstance.equals("AD")) {
                    documentId = cInstance + " " + year + " nr " + page;
                } else if (cInstance.equals("NJA")) {
                    documentId = cInstance + " " + year + " s. " + page;
                } else if (cInstance.equals("MÖD")) {
                    documentId = cInstance + " " + year + ":" + page;
                } else if (cInstance.equals("MIG")) {
                    documentId = cInstance + " " + year + ":" + page;
                } else if (cInstance.equals("RK")) {
                    documentId = cInstance + " " + year + ":" + page;
                } else if (cInstance.equals("MD")) {
                    documentId = cInstance + " " + year + ":" + page;
                } else if (cInstance.equals("RÅ")) {
                    documentId = cInstance + " " + year + " ref. " + page;
                } else if (cInstance.equals("HFD")) {
                    documentId = cInstance + " " + year + " ref. " + page;
                } else if (cInstance.equals("RH")) {
                    documentId = cInstance + " " + year + ":" + page;
                }
            }
            System.out.println(documentId);
        }
    }
}
