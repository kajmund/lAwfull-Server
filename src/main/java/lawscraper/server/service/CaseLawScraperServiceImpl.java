package lawscraper.server.service;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.server.scrapers.ZipDataUtil;
import lawscraper.server.scrapers.caselawscraper.CaseLawScraper;
import lawscraper.shared.scraper.LawScraperSource;
import lawscraper.shared.scraper.ScraperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 2/24/12
 * Time: 9:07 AM
 */

@Service("CaseLawScraperServiceImpl")
@Transactional(readOnly = true)
public class CaseLawScraperServiceImpl implements CaseLawScraperService {

    PartFactory partFactory;
    RepositoryBase<CaseLaw> caseLawRepository;
    RepositoryBase<DocumentPart> lawRepository;


    @Autowired
    public CaseLawScraperServiceImpl(RepositoryBase<CaseLaw> caseLawRepository,
                                     RepositoryBase<DocumentPart> lawRepository,
                                     PartFactory partFactory) {
        this.caseLawRepository = caseLawRepository;
        this.partFactory = partFactory;
        this.lawRepository = lawRepository;
        this.caseLawRepository.setEntityClass(CaseLaw.class);
        this.lawRepository.setEntityClass(DocumentPart.class);
        System.out.println("ScraperService instantiated");
    }

    @Override
    @Transactional(readOnly = false)
    public ScraperStatus scrapeCaseLaws(LawScraperSource lawScraperSource) throws IOException, SAXException,
                                                                                  ParserConfigurationException {
        switch (lawScraperSource) {
            case INTERNET:
                return scrapeLawsFromInternet();
            case ZIPFILE:
                return scrapeLawsFromZipFile();
            default:
                throw new IllegalArgumentException();
        }
    }


    @Override
    @Transactional(readOnly = false)
    public ScraperStatus scrapeLawsFromZipFile() throws IOException, SAXException, ParserConfigurationException {
        ScraperStatus scraperStatus = new ScraperStatus();
        CaseLawScraper scraper;
        for (ZipDataUtil.CaseLawEntry caseLawEntry : ZipDataUtil.getAllCaseLaws()) {
            scraper = new CaseLawScraper(lawRepository);
            System.out.println("--");
            System.out.println("Scraping...");
            try {
                scraper.parse(caseLawEntry.getInputStream());
            } catch (org.xml.sax.SAXParseException e) {
                System.out.println("Made a popo :(");
            }
            scraperStatus.increaseScrapedLaws();

            System.out.println("Done: Scraped laws: " + scraperStatus.getScrapedLaws());
            saveCaseLaw(scraper);
        }
        return scraperStatus;
    }

    @Override
    @Transactional(readOnly = false)
    public void saveCaseLaw(CaseLawScraper scraper) {
        if (scraper.getCaseLaw().getKey() != null) {
            CaseLaw savedCaseLaw = caseLawRepository.save(scraper.getCaseLaw());
            System.out.println("-- ID: " + savedCaseLaw.getId());
        } else {
            System.out.println("Didnt have a key therefor the caselaw was expunged");
        }
    }


    private ScraperStatus scrapeLawsFromInternet() {
        ScraperStatus scraperStatus = new ScraperStatus();
        return scraperStatus;
    }
}
