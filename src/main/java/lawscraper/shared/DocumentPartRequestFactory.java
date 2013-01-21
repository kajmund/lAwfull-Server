package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.DocumentService;
import lawscraper.shared.proxies.DocumentPartProxy;
import lawscraper.shared.proxies.HTMLProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface DocumentPartRequestFactory extends RequestFactory {
    @Service(value = DocumentService.class, locator = SpringServiceLocator.class)
    interface DocumentRequest extends RequestContext {
        Request<List<DocumentPartProxy>> getDocumentsByLegalResearch(Long id);

        Request<HTMLProxy> getDocumentCommentary(String documentId);
    }

    DocumentRequest documentRequest();
}
