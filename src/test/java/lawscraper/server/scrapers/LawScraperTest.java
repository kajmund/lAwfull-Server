package lawscraper.server.scrapers;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.server.scrapers.lawscraper.LawScraper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 1/15/12
 * Time: 7:14 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/dataSource.xml",
                                   "classpath:/applicationContext-security.xml"})

@TransactionConfiguration(defaultRollback = true)
public class LawScraperTest {

    @Autowired
    private RepositoryBase<DocumentPart> documentRepository;

    @Before
    public void setup() {
        this.documentRepository.setEntityClass(DocumentPart.class);
    }

    @Test
    public void testParseLawSkadestandsLagen() throws Exception {
        LawScraper scraper = new LawScraper(documentRepository);

        for (ZipDataUtil.LawEntry lawEntry : ZipDataUtil.getAllLaws()) {
            //scraper.parse(ZipDataUtil.getLaw("1972:207"));
            if (!lawEntry.getName().contains(".xht2")) {
                continue;
            }

            scraper.parse(lawEntry.getInputStream());
            System.out.println("Scraping " + lawEntry.getName());
            Law law = scraper.getLaw();
            assertNotNull(law);
        }
        /*
        assertEquals(" Skadeståndslag (1972:207) ", law.getTitle());

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
        assertEquals(1, chapter.getOrder());
        assertEquals(3, chapter.getChildren().size());

        LawDocumentPart paragraph = chapter.getChildren().iterator().next();
        */
    }


}
