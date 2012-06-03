package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.LegalResearchService;
import lawscraper.shared.proxies.LegalResearchProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface LegalResearchRequestFactory extends RequestFactory {
    @Service(value = LegalResearchService.class, locator = SpringServiceLocator.class)
    interface LegalResearchRequest extends RequestContext {
        Request<LegalResearchProxy> find(Long id);

        Request<Void> addLegalResearch(String title, String description);

        Request<List<LegalResearchProxy>> findLegalResearchByLoggedInUser();

        Request<Void> setLegalResearchActive(Long legalResearchId);
    }

    LegalResearchRequest legalResearchRequest();
}
