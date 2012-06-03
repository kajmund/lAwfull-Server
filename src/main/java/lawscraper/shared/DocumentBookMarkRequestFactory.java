package lawscraper.shared;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import lawscraper.server.locators.SpringServiceLocator;
import lawscraper.server.service.DocumentBookMarkService;
import lawscraper.shared.proxies.DocumentBookMarkProxy;

import java.util.List;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/27/11
 * Time: 7:52 AM
 */

public interface DocumentBookMarkRequestFactory extends RequestFactory {
    @Service(value = DocumentBookMarkService.class, locator = SpringServiceLocator.class)
    interface DocumentBookMarkRequest extends RequestContext {
        Request<Void> addBookMark(Long documentPartId);

        Request<Void> removeBookMark(Long lawDocumentPartId);

        Request<List<DocumentBookMarkProxy>> findBookMarksByLawId(Long lawId);
    }

    DocumentBookMarkRequest documentBookMarkRequest();
}
