package lawscraper.server.scrapers;

import lawscraper.server.scrapers.lawscraper.LawUrlScraper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 1/21/12
 * Time: 12:01 AM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class LawUrlScraperTest {
    @Test
    public void testLawUrlScraper() {
        LawUrlScraper lawUrlScraper = new LawUrlScraper();
        try {
            lawUrlScraper.fetchUrls();
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
