package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.LawScraperService;
import lawscraper.shared.proxies.ScraperStatusProxy;
import lawscraper.shared.scraper.LawScraperSource;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface LawScraperRequestFactory extends RequestFactory{
    @Service(value = LawScraperService.class, locator = SpringServiceLocator.class)
    interface LawScraperRequest extends RequestContext{
        Request<ScraperStatusProxy> scrapeLaws(LawScraperSource lawScraperSource);
    }

    LawScraperRequest lawScraperRequest();
}
