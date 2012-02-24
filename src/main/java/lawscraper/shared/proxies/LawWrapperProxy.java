package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.service.LawWrapper;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 12/26/11
 * Time: 10:22 AM
 */

@ProxyFor(value = LawWrapper.class)
public interface LawWrapperProxy extends ValueProxy {
    Long getId();
    String getTitle();
}
