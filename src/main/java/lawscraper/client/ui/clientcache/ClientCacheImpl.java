package lawscraper.client.ui.clientcache;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;
import lawscraper.shared.proxies.HTMLProxy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by erik, IT Bolaget Per & Per AB
 * Copyright Inspectera AB
 * Date: 8/10/12
 * Time: 9:42 PM
 */
public class ClientCacheImpl implements ClientCache {
    private Storage cache = null;
    private int size;


    public ClientCacheImpl() {
        cache = Storage.getLocalStorageIfSupported();
        if (cache == null) {
            System.err.println("ClientCache::LocalStorage not supported");
        }
        figureOutSizeOfStorage();
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return new StorageMap(cache).entrySet().iterator();
    }

    @Override
    public Set<String> getTabs() {
        StorageMap storageMap = new StorageMap(cache);
        Set<String> tabs = new HashSet<String>();
        for (String docKey : storageMap.keySet()) {
            JSONObject jsonObject = string2JSONObject(cache.getItem(docKey));

            if (jsonObject.get("openTab").equals("true")) {
                tabs.add(docKey);
            }
        }
        return tabs;
    }

    public void removeTab(String key) {
        JSONObject jsonObject = string2JSONObject(cache.getItem(key));
        jsonObject.put("openTab", new JSONString("false"));
        cache.setItem(key, jsonObject.toString());
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
        jsonObject.put("openTab", new JSONString("true"));

        String jsonString = jsonObject.toString();
        cache.setItem(key, jsonString);
        size += jsonString.length();
    }

    @Override
    public HTMLProxy get(String key) {
        String jsonString = cache.getItem(key);
        final JSONObject jsonObject = string2JSONObject(jsonString);
        HTMLProxy proxy = null;

        if (jsonObject != null) {
            proxy = new HTMLProxy() {
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
        }
        return proxy;
    }

    private JSONObject string2JSONObject(String jsonString) {
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
        return jsonObject;
    }

    @Override
    public int getLength() {
        return cache.getLength();
    }
}
