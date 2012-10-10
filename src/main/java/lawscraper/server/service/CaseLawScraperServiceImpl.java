package lawscraper.server.service;

import lawscraper.server.components.PartFactory;
import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.repositories.RepositoryBase;
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

    PartFactory partFactory;
    RepositoryBase<CaseLaw> caseLawRepository;

    @Autowired
    public CaseLawScraperServiceImpl(RepositoryBase<CaseLaw> caseLawRepository, PartFactory partFactory) {
        this.caseLawRepository = caseLawRepository;
        this.partFactory = partFactory;
        this.caseLawRepository.setEntityClass(CaseLaw.class);
        System.out.println("ScraperService instantiated");
    }

    @Override
    @Transactional(readOnly = false)
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


    @Override
    @Transactional(readOnly = false)
    public ScraperStatus scrapeLawsFromZipFile() {
        ScraperStatus scraperStatus = new ScraperStatus();
        CaseLawScraper scraper;
        try {
            int scrapedCases = 10;
            for (ZipDataUtil.CaseLawEntry caseLawEntry : ZipDataUtil.getAllCaseLaws()) {
                scraper = new CaseLawScraper();
                try {
                    System.out.println("--");
                    System.out.println("Scraping...");
                    scraper.parse(caseLawEntry.getInputStream());
                    scraperStatus.increaseScrapedLaws();
                    CaseLaw savedCaseLaw;
                    System.out.println("Done: Scraped laws: " + scraperStatus.getScrapedLaws());
                    if (scraper.getCaseLaw().getDocumentKey() != null) {
                        savedCaseLaw = caseLawRepository.save(scraper.getCaseLaw());
                        System.out.println("-- ID: " + savedCaseLaw.getId());
                    } else {
                        System.out.println("Didnt have a key therefor the caselaw was expunged");
                    }

                    if (scrapedCases-- < 1) {
                        break;
                    }

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
