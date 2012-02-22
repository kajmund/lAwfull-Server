package lawscraper.server.scraper;

import lawscraper.server.service.TextService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Test that all laws are parseable without error.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
public class TransactionParserMassTest {

    @Autowired
    TextService textService;

    @Test
    public void parseAllLaws() throws Exception {
        int lawCount = 0;
        int successCount = 0;
        for (TestDataUtil.Law law : TestDataUtil.getAllLaws()) {
            lawCount++;
            Scraper scraper = new Scraper(textService);
            try {
                scraper.parse(law.getInputStream());
                successCount++;
            } catch (Exception e) {
                System.out.println("Failed to parse " + law.getName());
                e.printStackTrace();
            }

        }
        assertEquals("All laws not parseable", lawCount, successCount);
    }

}
