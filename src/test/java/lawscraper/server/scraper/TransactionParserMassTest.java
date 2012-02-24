package lawscraper.server.scraper;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.law.Law;
import lawscraper.server.service.LawService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test that all laws are parseable without error.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
public class TransactionParserMassTest {

    @Autowired
    LawService lawService;
    @Autowired
    PartFactory partFactory;

    @Test
    @Ignore
    public void parseAllLaws() throws Exception {
        int lawCount = 0;
        int successCount = 0;
        long start = System.currentTimeMillis();
        for (TestDataUtil.Law law : TestDataUtil.getAllLaws()) {
            lawCount++;
            Scraper scraper = new Scraper(partFactory);
            try {
                scraper.parse(law.getInputStream());
                successCount++;
                lawService.createOrUpdate(scraper.getLaw());

            } catch (Exception e) {
                System.out.println("Failed to parse " + law.getName());
                e.printStackTrace();
            }

        }

        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
        Law law = lawService.find((long) 1);
        assertNotNull(law);
        assertEquals("All laws not parseable", lawCount, successCount);
    }

}
