package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.entities.superclasses.Document.DocumentPart;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 12/26/11
 * Time: 10:22 AM
 */

@ProxyFor(value = DocumentPart.class)
public interface DocumentPartProxy extends ValueProxy {
    String getType();

    String getKey();

    String getTitle();

    String getDescription();

}
