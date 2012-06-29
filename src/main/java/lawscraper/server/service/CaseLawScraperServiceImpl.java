package lawscraper.server.service;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.repositories.CaseLawRepository;
import lawscraper.server.scrapers.ZipDataUtil;
import lawscraper.server.scrapers.caselawscraper.CaseLawScraper;
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

@Service("CaseLawScraperServiceImpl")
@Transactional(readOnly = true)
public class CaseLawScraperServiceImpl implements CaseLawScraperService {

    @Autowired
    PartFactory partFactory;
    @Autowired
    CaseLawRepository caseLawRepository;

    public CaseLawScraperServiceImpl() {
        System.out.println("ScraperService instantiated");
    }

    @Override
    public ScraperStatus scrapeCaseLaws(LawScraperSource lawScraperSource) {
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
            for (ZipDataUtil.CaseLawEntry caseLawEntry : ZipDataUtil.getAllCaseLaws()) {
                CaseLawScraper scraper = new CaseLawScraper();
                try {
                    scraper.parse(caseLawEntry.getInputStream());
                    scraperStatus.increaseScrapedLaws();
                    CaseLaw scrapedLaw = scraper.getCaseLaw();
                    CaseLaw result = caseLawRepository.save(scrapedLaw);

                    //Output caselaw info
                    System.out.println("--");
                    System.out.println(result.getRelation());
                    System.out.println(result.getId());
                    System.out.println("--");

                } catch (Exception e) {
                    System.out.println("Failed to parse " + caseLawEntry.getName());
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
