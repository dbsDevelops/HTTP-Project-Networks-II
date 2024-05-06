package http.project.networks.ii.client;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CachedData {
    public HashMap<String, String> cachedData;


    public CachedData() {
        this.cachedData = new HashMap<>();
    }

    public void addData(String key, String value) {
        this.cachedData.put(key, value);
    }

    public String getData(String key) {
        return this.cachedData.get(key);
    }

    public void removeData(String key) {
        this.cachedData.remove(key);
    }

    public void clearData() {
        this.cachedData.clear();
    }

    public boolean containsKey(String key) {
        return this.cachedData.containsKey(key);
    }

    public boolean containsValue(String value) {
        return this.cachedData.containsValue(value);
    }

    public boolean isEmpty() {
        return this.cachedData.isEmpty();
    }

    public int size() {
        return this.cachedData.size();
    }

    public String toString() {
        return this.cachedData.toString();
    }

    public void clear() {
        this.cachedData.clear();
    }

    public void setCachedData(String data){
        Set<String> keys = this.cachedData.keySet();
        for(String key : keys){
            if(this.cachedData.get(key).isEmpty()){
                this.cachedData.put(key, data);
            }
        }
    }

    public void addKey(String url) {
        this.cachedData.put(url, "");
    }
}
