package lawscraper.server.scraper;

import junit.framework.TestCase;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/15/12
 * Time: 10:10 PM
 */
public class UtilitiesTest extends TestCase {
    public void testTrimText() throws Exception {
        String data = "   ";
        data = Utilities.trimText(data);
        assertEquals("", data);
    }
}
