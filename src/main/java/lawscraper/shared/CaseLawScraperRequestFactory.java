package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.CaseLawScraperService;
import lawscraper.shared.proxies.ScraperStatusProxy;
import lawscraper.shared.scraper.LawScraperSource;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface CaseLawScraperRequestFactory extends RequestFactory{
    @Service(value = CaseLawScraperService.class, locator = SpringServiceLocator.class)
    interface CaseLawScraperRequest extends RequestContext{
        Request<ScraperStatusProxy> scrapeCaseLaws(LawScraperSource lawScraperSource);
    }

    CaseLawScraperRequest caseLawScraperRequest();
}
