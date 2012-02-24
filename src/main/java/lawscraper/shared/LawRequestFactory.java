package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.LawService;
import lawscraper.shared.proxies.HTMLProxy;
import lawscraper.shared.proxies.LawWrapperProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface LawRequestFactory extends RequestFactory{
    @Service(value = LawService.class, locator = SpringServiceLocator.class)
    interface LawRequest extends RequestContext{
        Request<LawWrapperProxy> find(Long id);
        Request<List<LawWrapperProxy>> findAll();
        Request<HTMLProxy>findLawHTMLWrapped(Long id);
    }

    LawRequest lawRequest();
}
