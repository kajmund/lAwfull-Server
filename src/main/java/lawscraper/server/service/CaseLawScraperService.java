package lawscraper.server.service;

import lawscraper.server.scrapers.caselawscraper.CaseLawScraper;
import lawscraper.shared.scraper.LawScraperSource;
import lawscraper.shared.scraper.ScraperStatus;
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
public interface CaseLawScraperService {
    ScraperStatus scrapeCaseLaws(LawScraperSource lawScraperSource) throws IOException, SAXException,
                                                                           ParserConfigurationException;

    ScraperStatus scrapeLawsFromZipFile() throws IOException, SAXException, ParserConfigurationException;

    @Transactional(readOnly = false)
    void saveCaseLaw(CaseLawScraper scraper);
}
