package lawscraper.server.scraper;

import lawscraper.server.components.DummyPartFactory;
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
@ContextConfiguration(locations = "/text-context.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class LawPesistanceFunctionalTest {

    @Autowired
    private LawRepository lawRepository;
    @Autowired
    private PartFactory partFactory;
    @Autowired
    Neo4jOperations operations;


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
        law.setTitle("Law X");
        LawDocumentPart subPart = partFactory.createpart(LawDocumentPartType.DIVIDER);
        subPart.setKey("Key");
        operations.save(subPart);
        law.addDocumentPartChild(subPart);
        law = lawRepository.save(law);

        Law storedLaw = lawRepository.findOne(law.getId());
        assertNotNull(storedLaw);
        assertEquals("Law X", storedLaw.getTitle());
        LawDocumentPart part = storedLaw.getParts().iterator().next();
        assertEquals("Key", part.getKey());
    }

    @Test
    public void parseAndStoreFull() throws Exception {
        InputStream law = TestDataUtil.getLaw("1999:1229"); // Inkomstskattelagen
        Scraper scraper = new Scraper(new DummyPartFactory());
        scraper.parse(law);
        lawRepository.save(scraper.getLaw());
        Law storedLaw = lawRepository.findOne(scraper.getLaw().getId());
        assertNotNull(storedLaw);
    }

}
