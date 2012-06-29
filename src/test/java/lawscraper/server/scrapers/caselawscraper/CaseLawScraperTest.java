package lawscraper.server.scrapers.caselawscraper;

import lawscraper.server.entities.caselaw.CaseLaw;
import lawscraper.server.scrapers.ZipDataUtil;
import lawscraper.shared.scraper.ScraperStatus;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 6/16/12
 * Time: 1:06 PM
 */

public class CaseLawScraperTest {
    @Test
    public void testScrapeCaseLaw() {
        ScraperStatus scraperStatus = new ScraperStatus();
        try {
            for (ZipDataUtil.CaseLawEntry caseLawEntry : ZipDataUtil.getAllCaseLaws()) {

                CaseLawScraper scraper = new CaseLawScraper();
                try {
                    scraper.parse(caseLawEntry.getInputStream());
                    CaseLaw caseLaw = scraper.getCaseLaw();
                    scraperStatus.increaseScrapedLaws();

                } catch (Exception e) {
                    System.out.println("Failed to parse " + caseLawEntry.getName());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
