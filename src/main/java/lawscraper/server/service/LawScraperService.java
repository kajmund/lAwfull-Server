package lawscraper.server.service;

import lawscraper.shared.scraper.LawScraperSource;
import lawscraper.shared.scraper.ScraperStatus;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 9:07 AM
 */
public interface LawScraperService {
    ScraperStatus scrapeLaws(LawScraperSource lawScraperSource);
}
