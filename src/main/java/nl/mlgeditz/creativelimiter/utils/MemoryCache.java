package nl.mlgeditz.creativelimiter.utils;

import java.lang.ref.SoftReference;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.mlgeditz.creativelimiter.CreativeLimiter;

public class MemoryCache {

    private static final int SYNC_PERIOD_IN_MINUTES = 10;
    private final ConcurrentHashMap<String, SoftReference<CacheObject>> cache = new ConcurrentHashMap<>();

    public MemoryCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(SYNC_PERIOD_IN_MINUTES * 60000);
                    sync();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    public void add(String key, Object value) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            cache.put(key, new SoftReference<>(new CacheObject(value)));
        }
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public Object get(String key) {
        return Optional.ofNullable(cache.get(key)).map(SoftReference::get).map(CacheObject::getValue).orElse(null);
    }

    public void clear() {
        cache.clear();
    }

    public long size() {
        return (long) cache.entrySet().size();
    }

    public void sync() {
        if (size() > 0) {
            //Sync from cache to database
            try {
                Iterator<Entry<String, SoftReference<CacheObject>>> it = cache.entrySet().iterator();
                ArrayList<String> currentData = getDatabaseStorage();
                while (it.hasNext()) {
                    Map.Entry<String, SoftReference<CacheObject>> pair = it.next();
                    if (!currentData.contains(pair.getKey().toString())) {
                        CreativeLimiter.thdb().getNewStatement().executeUpdate("INSERT INTO block VALUES ('" + pair.getKey() + "')");
                    }
                }
            } catch (SQLException e) {
                Logger.log(Logger.Severity.ERROR, "Something went wrong while syncing data from cache to the database! Error: " + e.getMessage());
            }
        }
        this.clear();

        //Sync from database to cache
        try {
            ResultSet result = CreativeLimiter.thdb().getNewStatement().executeQuery("SELECT * FROM block");
            while (result.next()) {
                String location = result.getString("loc");
                add(location, "LOCATION");
            }
        } catch (SQLException e) {
            Logger.log(Logger.Severity.ERROR, "Something went wrong while syncing data from the database to cache! Error: " + e.getMessage());
        }
    }

    public ArrayList<String> getDatabaseStorage() {
        ArrayList<String> data = new ArrayList<>();
        try {
            ResultSet result = CreativeLimiter.thdb().getNewStatement().executeQuery("SELECT loc FROM block");
            while (result.next()) {
                String location = result.getString("loc");
                data.add(location);
            }
        } catch (SQLException e) {
            Logger.log(Logger.Severity.ERROR, "Something went wrong while getting data from the database to cache! Error: " + e.getMessage());
        }
        return data;
    }




    @AllArgsConstructor
    private static class CacheObject {
        @Getter private Object value;
    }
}
