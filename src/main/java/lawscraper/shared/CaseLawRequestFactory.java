package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.CaseLawService;
import lawscraper.shared.proxies.CaseLawProxy;
import lawscraper.shared.proxies.HTMLProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface CaseLawRequestFactory extends RequestFactory {
    @Service(value = CaseLawService.class, locator = SpringServiceLocator.class)
    interface CaseLawRequest extends RequestContext {
        Request<CaseLawProxy> find(Long id);

        Request<HTMLProxy> findCaseLawHTMLWrapped(Long id);

        Request<List<CaseLawProxy>> findCaseLawByQuery(String query);

        Request<List<CaseLawProxy>> getCaseLawsByYearAndCourt(String year, String court);
    }

    CaseLawRequest caseLawRequest();
}
