package lawscraper.shared.proxies;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import lawscraper.server.service.HTMLWrapper;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 2/24/12
 * Time: 11:00 AM
 */
@ProxyFor(value = HTMLWrapper.class)
public interface HTMLProxy extends ValueProxy {
    String getHtml();
    String getName();
    String getLawKey();
    //add Long id and ViewType viewType
}
