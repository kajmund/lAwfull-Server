package lawscraper.server.scraper;

import lawscraper.server.components.DummyPartFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that all laws are parseable without error.
 */
public class ParserMassTest {

    @Test
    public void parseAllLaws() throws Exception {
        int lawCount = 0;
        int successCount = 0;
        for (TestDataUtil.LawEntry lawEntry : TestDataUtil.getAllLaws()) {
            lawCount++;
            Scraper scraper = new Scraper(new DummyPartFactory());
            try {
                scraper.parse(lawEntry.getInputStream());
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to parse " + lawEntry.getName());
                e.printStackTrace();
            }

        }
        assertEquals("All laws not parseable", lawCount, successCount);
    }

}
