package lawscraper.server.scraper;

import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;

/**
 * TODO Fixa problem med att parsa index.html med SAXParser.
 */
public class LawTopicScraperTest {
    @Test
    @Ignore
    public void testScrape() throws Exception {
        LawTopicScraper scraper = new LawTopicScraper();
        scraper.scrape(new FileInputStream(TestDataUtil.getFile("/lawscraper/index.xml")));
        assertEquals(20, scraper.getTopics().size());
    }
}
