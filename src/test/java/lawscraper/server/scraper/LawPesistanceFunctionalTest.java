package lawscraper.server.scraper;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.law.LawDocumentPart;
import lawscraper.server.entities.law.LawDocumentPartType;
import lawscraper.server.repositories.LawRepository;
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
    LawRepository lawRepository;
    @Autowired
    PartFactory partFactory;
    @Autowired
    Neo4jOperations template;


    @Test
    public void parseAndStoreMinimal() throws Exception {
        Law update = lawRepository.save(new Law());

        Law storedLaw = lawRepository.findOne(update.getId());
        assertNotNull(storedLaw);
    }

    @Test
    public void parseAndStoreSingePart() throws Exception {
        Law law = new Law();
        law = lawRepository.save(law);
        //law = template.save(law);
        law.setTitle("Law X");
        LawDocumentPart subPart = partFactory.createpart(LawDocumentPartType.DIVIDER);
        subPart.setKey("Key");
        law.addDocumentPartChild(subPart);
        lawRepository.save(law);

        Law storedLaw = lawRepository.findOne(law.getId());
        assertNotNull(storedLaw);
        assertEquals("Law X", storedLaw.getTitle());
        assertEquals("Key", storedLaw.getParts().iterator().next().getKey());
    }

    @Test
    public void parseAndStoreFull() throws Exception {
        InputStream law = TestDataUtil.getLaw("2011:926");
        Scraper scraper = new Scraper(new DummyPartFactory());
        scraper.parse(law);
        lawRepository.save(scraper.getLaw());
        Law storedLaw = lawRepository.findOne(scraper.getLaw().getId());
        assertNotNull(storedLaw);
    }

}
