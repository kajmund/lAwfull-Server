package lawscraper.server.scrapers.caselawscraper;

import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.server.scrapers.ZipDataUtil;
import lawscraper.server.service.CaseLawScraperService;
import lawscraper.shared.scraper.ScraperStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 6/16/12
 * Time: 1:06 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/dataSource.xml",
                                   "classpath:/applicationContext-security.xml"})

@TransactionConfiguration(defaultRollback = true)
public class CaseLawScraperTest {
    @Autowired
    RepositoryBase<CaseLaw> caseLawRepository;
    @Autowired
    RepositoryBase<DocumentPart> lawRepository;
    @Autowired
    CaseLawScraperService caseLawScraperService;

    @Before
    public void setup() {
        this.caseLawRepository.setEntityClass(CaseLaw.class);
        this.lawRepository.setEntityClass(DocumentPart.class);
    }

    @Test
    public void testScrapeCaseLawService() {
        try {
            caseLawScraperService.scrapeLawsFromZipFile();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testScrapeCaseLaw() {
        this.caseLawRepository.setEntityClass(CaseLaw.class);
        this.lawRepository.setEntityClass(DocumentPart.class);

        ScraperStatus scraperStatus = new ScraperStatus();
        try {
            for (ZipDataUtil.CaseLawEntry caseLawEntry : ZipDataUtil.getAllCaseLaws()) {

                CaseLawScraper scraper = new CaseLawScraper(lawRepository);
                if (caseLawEntry.getName().contains(".xht2")) {
                    try {
                        scraper.parse(caseLawEntry.getInputStream());
                        CaseLaw caseLaw = scraper.getCaseLaw();
                        scraperStatus.increaseScrapedLaws();
                        caseLaw.getDocumentReferenceList();

                    } catch (Exception e) {
                        System.out.println("Failed to parse " + caseLawEntry.getName());
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
