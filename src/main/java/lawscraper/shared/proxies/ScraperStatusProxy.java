package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.shared.scraper.ScraperStatus;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 11:43 AM
 */
@ProxyFor(value = ScraperStatus.class)
public interface ScraperStatusProxy extends ValueProxy{
    int getScrapedLaws();
}
