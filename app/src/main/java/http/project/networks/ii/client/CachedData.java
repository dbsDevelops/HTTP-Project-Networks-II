package http.project.networks.ii.client;

import java.util.HashMap;

/**
 * The {@code CachedData} class represents a simple cache for storing key-value pairs.
 */
public class CachedData {
    /** The underlying HashMap to store the cached data. */
    public HashMap<String, String> cachedData;

    /**
     * Constructs a {@code CachedData} instance.
     */
    public CachedData() {
        this.cachedData = new HashMap<>();
    }

    /**
     * Adds data to the cache.
     *
     * @param key The key to associate with the value.
     * @param value The value to be cached.
     */
    public void addData(String key, String value) {
        this.cachedData.put(key, value);
    }

    /**
     * Retrieves data from the cache based on the given key.
     *
     * @param key The key of the data to retrieve.
     * @return The value associated with the specified key, or {@code null} if the key is not found.
     */
    public String getData(String key) {
        return this.cachedData.get(key);
    }

    /**
     * Removes data from the cache based on the given key.
     *
     * @param key The key of the data to remove.
     */
    public void removeData(String key) {
        this.cachedData.remove(key);
    }

    /**
     * Clears all data from the cache.
     */
    public void clearData() {
        this.cachedData.clear();
    }

    /**
     * Checks if the cache contains the specified key.
     *
     * @param key The key to check for.
     * @return {@code true} If the cache contains the key, otherwise {@code false}.
     */
    public boolean containsKey(String key) {
        return this.cachedData.containsKey(key);
    }

    /**
     * Checks if the cache contains the specified value.
     *
     * @param value The value to check for.
     * @return {@code true} If the cache contains the value, otherwise {@code false}.
     */
    public boolean containsValue(String value) {
        return this.cachedData.containsValue(value);
    }

    /**
     * Checks if the cache is empty.
     *
     * @return {@code true} If the cache is empty, otherwise {@code false}.
     */
    public boolean isEmpty() {
        return this.cachedData.isEmpty();
    }

    /**
     * Returns the size of the cache.
     *
     * @return The number of key-value mappings in the cache.
     */
    public int size() {
        return this.cachedData.size();
    }

    /**
     * Returns a string representation of the cache.
     *
     * @return A string representation of the cache.
     */
    public String toString() {
        return this.cachedData.toString();
    }

    /**
     * Clears all data from the cache.
     */
    public void clear() {
        this.cachedData.clear();
    }
}
