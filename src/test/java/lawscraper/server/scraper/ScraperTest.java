package lawscraper.server.scraper;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.*;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 1/15/12
 * Time: 7:14 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class ScraperTest {

    @Test
    public void testParseLawSkadestandsLagen() throws Exception {
        Scraper scraper = new Scraper();
        Law law = scraper.parseLaw(TestDataUtil.getLaw("1972:207"));
        assertNotNull(law);
        //assertEquals(" Skadeståndslag (1972:207) ", law.getTitle());
        assertEquals("1972:207", law.getFsNumber());
        assertTrue(law.getLatestFetchFromGov().length() > 0);
        assertEquals("1972-06-02", law.getReleaseDate());
        assertEquals("Regeringskansliet", law.getPublisher());
        assertEquals("Justitiedepartementet L2", law.getCreator());
        assertEquals(42, law.getPropositions().size());
        assertEquals(6, law.getChildren().size());

        LawDocumentPart chapter = law.getChildren().iterator().next();
        assertEquals("#K1", chapter.getKey());
        //assertEquals(chapter.getHeadLine(), "  1 kap. Inledande bestämmelser ");
        assertEquals("1", chapter.getOrder());
        assertEquals(3,chapter.getChildren().size());

        LawDocumentPart paragraph = chapter.getChildren().iterator().next();
    }
}
