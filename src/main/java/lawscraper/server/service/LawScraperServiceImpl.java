package lawscraper.server.service;

import lawscraper.server.entities.law.Law;
import lawscraper.server.entities.superclasses.Document.DocumentPart;
import lawscraper.server.repositories.RepositoryBase;
import lawscraper.server.scrapers.ZipDataUtil;
import lawscraper.server.scrapers.lawscraper.LawScraper;
import lawscraper.shared.scraper.LawScraperSource;
import lawscraper.shared.scraper.ScraperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 2/24/12
 * Time: 9:07 AM
 */

@Service("LawScraperServiceImpl")
@Transactional(readOnly = true)
public class LawScraperServiceImpl implements LawScraperService {

    @Qualifier("repositoryBaseImpl")
    @Autowired
    private RepositoryBase<Law> lawRepository;

    @Autowired
    private RepositoryBase<DocumentPart> documentPartRepository;

    public LawScraperServiceImpl() {
        System.out.println("ScraperService instantiated");
    }

    @Override
    @Transactional(readOnly = false)
    public ScraperStatus scrapeLaws(LawScraperSource lawScraperSource) {
        switch (lawScraperSource) {
            case INTERNET:
                return scrapeLawsFromInternet();
            case ZIPFILE:
                return scrapeLawsFromZipFile();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = false)
    private ScraperStatus scrapeLawsFromZipFile() {
        ScraperStatus scraperStatus = new ScraperStatus();

        try {
            int scrapedLaws = 50;
            for (ZipDataUtil.LawEntry lawEntry : ZipDataUtil.getAllLaws()) {
                /*
                if (scrapedLaws-- < 1) {
                    break;
                }
                */
                LawScraper scraper = new LawScraper(documentPartRepository);
                try {
                    scraper.parse(lawEntry.getInputStream());
                    scraperStatus.increaseScrapedLaws();
                    Law scrapedLaw = scraper.getLaw();
                    Law result = lawRepository.save(scrapedLaw);
                    System.out.println("--");
                    System.out.println(result.getTitle());
                    System.out.println(result.getId());
                    System.out.println("--");

                } catch (Exception e) {
                    System.out.println("Failed to parse " + lawEntry.getName());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scraperStatus;
    }

    private ScraperStatus scrapeLawsFromInternet() {
        return new ScraperStatus();
    }
}
