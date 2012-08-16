package lawscraper.client.ui.clientcache;

import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 8/10/12
 * Time: 9:42 PM
 */
public interface ClientCache {
    HTMLProxy get(String key);

    int getLength();

    int getSize();

    void add(String key, HTMLProxy htmlProxy);

    void setRequestFactory(LawRequestFactory requestFactory);
}
