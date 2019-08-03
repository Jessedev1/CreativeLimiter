package nl.mlgeditz.creativelimiter.utils;

import nl.mlgeditz.creativelimiter.CreativeLimiter;
import nl.mlgeditz.creativelimiter.config.FileManager;

public class ApiSync {

    public static void syncConfig() {
        FileManager config = new FileManager("config.yml");

        for(String string : config.getFile().getStringList("Deny-Placing")) {
            CreativeLimiter.getAPI().addPlaceBlocker(string);
        }

        for(String string : config.getFile().getStringList("Deny-Interact")) {
            CreativeLimiter.getAPI().addInteractBlocker(string);
        }


    }

}
