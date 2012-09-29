package lawscraper.client.ui.clientcache;

import lawscraper.shared.proxies.HTMLProxy;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 8/10/12
 * Time: 9:42 PM
 */
public interface ClientCache {
    HTMLProxy get(String key);

    int getLength();

    void add(String key, HTMLProxy htmlProxy);

    Iterator<Map.Entry<String,String>> iterator();

    Set<String> getTabs();
}
