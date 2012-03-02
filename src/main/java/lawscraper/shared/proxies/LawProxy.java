package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.entities.law.Law;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/26/11
 * Time: 10:22 AM
 */

@ProxyFor(value = Law.class)
public interface LawProxy extends ValueProxy {
    Long getId();
    String getTitle();
}
