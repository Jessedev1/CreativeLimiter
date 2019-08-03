package nl.mlgeditz.creativelimiter.config;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FileManager {

    private File file;
    private FileConfiguration fileConfiguration;

    public FileManager (String path) {
        file = new File(CreativeLimiter.pl.getDataFolder(), path);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void setPath(String path) {
        file = new File(CreativeLimiter.pl.getDataFolder(), path);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void createFile() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile() {
        if (file.exists()) file.delete();
    }

    public FileConfiguration getFile() {
        return fileConfiguration;
    }

    public void write(String key, Object value) {
        fileConfiguration.set(key, value);
    }

    public void write(HashMap<Object, Object> mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            fileConfiguration.set(pair.getKey().toString(), pair.getValue());
            it.remove();
        }
    }

    public void write(String key, ArrayList<String> list) {
        List<String> stringList = getFile().getStringList(key);
        stringList.addAll(list);
        this.add(key, stringList);
    }

    public void add(String key, Object value) {
        if (!fileConfiguration.isSet(key)) {
            write(key, value);
        }
    }

    public boolean contains(String key) {
        return fileConfiguration.contains(key);
    }

    public Object read(String key) {
        return fileConfiguration.get(key);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (file.exists()) fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
