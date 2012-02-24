package lawscraper.server.scraper;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import lawscraper.server.service.LawService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test that all laws are parseable without error.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class LawPesistanceFunctionalTest {

    @Autowired
    LawService lawService;
    @Autowired
    PartFactory partFactory;
    @Autowired
    Neo4jOperations template;


    @Test
    public void parseAndStoreMinimal() throws Exception {
        Law update = lawService.createOrUpdate(new Law());

        Law storedLaw = lawService.find(update.getId());
        assertNotNull(storedLaw);
    }

    @Test
    public void parseAndStoreSingePart() throws Exception {
        Law law = new Law();
        law = lawService.createOrUpdate(law);
        //law = template.save(law);
        law.setTitle("Law X");
        LawDocumentPart subPart = partFactory.createpart(LawDocumentPartType.DIVIDER);
        subPart.setKey("Key");
        law.addDocumentPartChild(subPart);
        lawService.createOrUpdate(law);

        Law storedLaw = lawService.find(law.getId());
        assertNotNull(storedLaw);
        assertEquals("Law X", storedLaw.getTitle());
        assertEquals("Key", storedLaw.getParts().iterator().next().getKey());
    }

    @Test
    public void parseAndStoreFull() throws Exception {
        InputStream law = TestDataUtil.getLaw("2011:926");
        Scraper scraper = new Scraper(new DummyPartFactory());
        scraper.parse(law);
        lawService.createOrUpdate(scraper.getLaw());
        Law storedLaw = lawService.find(scraper.getLaw().getId());
        assertNotNull(storedLaw);
    }

}
