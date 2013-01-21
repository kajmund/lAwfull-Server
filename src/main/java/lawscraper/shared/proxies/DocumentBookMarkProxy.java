package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.entities.documentbookmark.DocumentBookMark;

/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 3/10/12
 * Time: 5:26 PM
 */
@ProxyFor(value = DocumentBookMark.class)
public interface DocumentBookMarkProxy extends ValueProxy {
    Long getId();

    String getDocumentKey();

    String getTitle();

    String getDescription();

    void setId(Long id);

    void setTitle(String name);

    void setDescription(String description);
}
