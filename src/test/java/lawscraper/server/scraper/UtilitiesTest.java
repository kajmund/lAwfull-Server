package lawscraper.server.scraper;

import lawscraper.server.entities.law.LawDocumentPartType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/15/12
 * Time: 10:10 PM
 */
public class UtilitiesTest {

    @Test
    public void testTrimText() throws Exception {
        String data = "   ";
        data = Utilities.trimText(data);
        assertEquals("", data);
    }

    @Test
    public void testGetChapterByKey() {
        String key = "K12R22";
        assertEquals(LawDocumentPartType.CHAPTER, Utilities.getParentLawDocumentPartTypeByKey(key));
    }
}
