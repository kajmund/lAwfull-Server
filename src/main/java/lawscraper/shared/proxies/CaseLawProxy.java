package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.entities.caselaw.CaseLaw;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * <p/>
 * Date: 12/26/11
 * Time: 10:22 AM
 */

@ProxyFor(value = CaseLaw.class)
public interface CaseLawProxy extends ValueProxy {
    Long getId();

    String getDescription();

    String getKey();
}
