package lawscraper.server.service;

import lawscraper.server.components.LawStore;
import lawscraper.server.components.PartFactory;
import lawscraper.server.scraper.Scraper;
import lawscraper.server.scraper.TestDataUtil;
import lawscraper.shared.scraper.LawScraperSource;
import lawscraper.shared.scraper.ScraperStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 9:07 AM
 */

@Service("LawScraperServiceImpl")
public class LawScraperServiceImpl implements LawScraperService {

    @Autowired
    LawStore lawStore;
    @Autowired
    PartFactory partFactory;

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

    private ScraperStatus scrapeLawsFromZipFile() {
        ScraperStatus scraperStatus = new ScraperStatus();
        try {
            for (TestDataUtil.LawEntry lawEntry : TestDataUtil.getAllLaws()) {
                Scraper scraper = new Scraper(partFactory);
                try {
                    scraper.parse(lawEntry.getInputStream());
                    scraperStatus.increaseScrapedLaws();
                    //lawStore.persistLaw(scraper.getLaw());
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
