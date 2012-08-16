package lawscraper.client.ui.clientcache;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;
import lawscraper.shared.LawRequestFactory;
import lawscraper.shared.proxies.HTMLProxy;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 8/10/12
 * Time: 9:42 PM
 */
public class ClientCacheImpl implements ClientCache {
    private Storage cache = null;
    private int size;
    private LawRequestFactory requestFactory;

    public ClientCacheImpl() {
        this.requestFactory = requestFactory;
        cache = Storage.getLocalStorageIfSupported();
        if (cache == null) {
            System.err.println("ClientCache::LocalStorage not supported");
        }
        figureOutSizeOfStorage();
    }

    private int figureOutSizeOfStorage() {
        this.size = 0;
        StorageMap storageMap = new StorageMap(cache);
        for (String s : storageMap.keySet()) {
            String item = cache.getItem(s);
            this.size += item.length();
        }
        return size;
    }

    @Override
    public void add(String key, HTMLProxy htmlProxy) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("html", new JSONString(htmlProxy.getHtml()));
        jsonObject.put("name", new JSONString(htmlProxy.getName()));
        jsonObject.put("lawKey", new JSONString(htmlProxy.getLawKey()));

        String jsonString = jsonObject.toString();
        cache.setItem(key, jsonString);
        size += jsonString.length();
    }

    @Override
    public void setRequestFactory(LawRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Override
    public HTMLProxy get(String key) {
        String jsonString = cache.getItem(key);
        JSONValue jsonValue;
        try {
            jsonValue = JSONParser.parseLenient(jsonString);
        } catch (Exception e) {
            return null;
        }

        final JSONObject jsonObject = jsonValue.isObject();
        if (jsonObject == null) {
            System.err.println("jsonobject not parsable");
            return null;
        }

        HTMLProxy proxy = new HTMLProxy() {
            @Override
            public String getHtml() {
                return jsonObject.get("html").isString().stringValue();
            }

            @Override
            public String getName() {
                return jsonObject.get("name").isString().stringValue();
            }

            @Override
            public String getLawKey() {
                return jsonObject.get("lawKey").isString().stringValue();
            }
        };

        return proxy;
    }

    @Override
    public int getLength() {
        return cache.getLength();
    }

    @Override
    public int getSize() {
        return size;
    }
}
