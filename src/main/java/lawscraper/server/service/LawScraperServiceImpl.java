package lawscraper.server.service;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.law.Law;
import lawscraper.server.repositories.LawRepository;
import lawscraper.server.scraper.Scraper;
import lawscraper.server.scraper.TestDataUtil;
import lawscraper.shared.scraper.LawScraperSource;
import lawscraper.shared.scraper.ScraperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 9:07 AM
 */

@Service("LawScraperServiceImpl")
@Transactional(readOnly = true)
public class LawScraperServiceImpl implements LawScraperService {

    @Autowired
    PartFactory partFactory;
    @Autowired
    LawRepository lawRepository;

    public LawScraperServiceImpl() {
        System.out.println("ScraperService instantiated");
    }

    @Override
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
            for (TestDataUtil.LawEntry lawEntry : TestDataUtil.getAllLaws()) {
                Scraper scraper = new Scraper(partFactory);
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
        ScraperStatus scraperStatus = new ScraperStatus();
        return scraperStatus;
    }
}
