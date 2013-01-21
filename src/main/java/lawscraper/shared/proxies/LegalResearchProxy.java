package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.entities.legalresearch.LegalResearch;


/**
 * Created by erik, IT Bolaget Per & Per AB

 * Date: 4/19/12
 * Time: 9:08 PM
 */
@ProxyFor(value = LegalResearch.class)
public interface LegalResearchProxy extends ValueProxy {
    String getTitle();

    String getDescription();

    Long getId();

}
