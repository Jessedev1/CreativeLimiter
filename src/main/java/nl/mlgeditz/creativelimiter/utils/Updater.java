package nl.mlgeditz.creativelimiter.utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.mlgeditz.creativelimiter.CreativeLimiter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {

    private static Updater instance = null;

    private static String updaterURL = "https://updates.mlgeditz.nl/";
    private static String project = "CreativeLimiter";
    private static String apiKey = "bb48uZM3ZAb3gm5hAWeIUwQf9FZoeaktn9Xot5oaI0UN4geHWUp0GPp";
    private static boolean updateWhenCritical = true;

    /**
     * Get the instance of the Updater class.
     * @return instance of class
     */
    public static Updater getUpdater() {
        if (instance == null) {
            instance = new Updater();
        }
        return instance;
    }

    public boolean updatesAvailable() {
        Update latest = getLatestUpdate();
        if (latest == null) return false;

        String[] version = latest.getVersion().split("\\.");
        String[] currentVersion = CreativeLimiter.pl.getDescription().getVersion().split("\\.");

        if (version.length <= 1) return false;

        if (Integer.valueOf(version[0]) > Integer.valueOf(currentVersion[0])) return true;
        if (Integer.valueOf(version[1]) > Integer.valueOf(currentVersion[1])) return true;

        if (version.length >= 3) {
            if (Integer.valueOf(version[1]) >= Integer.valueOf(currentVersion[1])) {
                if (currentVersion.length <= 2 || Integer.valueOf(version[2]) > Integer.valueOf(currentVersion[2])) return true;
            }
        }
        return false;
    }

    public boolean checkForUpdates() {
        Logger.log(Logger.Severity.INFO, "Checking for updates...");
        if (updatesAvailable()) {
            Update latest = getLatestUpdate();
            Logger.log(Logger.Severity.INFO, "Found 1 new update. Please update soon!");

            if (latest.isCritical() && updateWhenCritical) {
                Logger.log(Logger.Severity.WARNING, "Found critical update... Installing!");
                downloadLatest(latest.getDownloadLink(), project, CreativeLimiter.pl);
                Logger.log(Logger.Severity.INFO, "Installation of " + project + " v" + latest.getVersion() + " finished! Reloading server!");
                Bukkit.getServer().reload();
            }
            return true;
        }
        Logger.log(Logger.Severity.INFO, "No updates are available right now! Try again later.");
        return false;
    }

    public void installUpdate() {
        Logger.log(Logger.Severity.INFO, "Checking for updates...");
        if (updatesAvailable()) {
            Update latest = getLatestUpdate();

            Logger.log(Logger.Severity.INFO, "Found update... Installing!");
            downloadLatest(latest.getDownloadLink(), project, CreativeLimiter.pl);

            Logger.log(Logger.Severity.INFO, "Installation of " + project + " v" + latest.getVersion() + " finished! Reloading...");
            Bukkit.getServer().reload();
        } else {
            Logger.log(Logger.Severity.INFO, "No updates are available right now! Try again later.");
        }
    }

    public Update getLatestUpdate() {
        try {
            URL url = new URL(updaterURL + "updates/?project=" + project + "&key=" + apiKey + "&version=latest");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("User-Agent", project + " UpdateChecker");
            http.setConnectTimeout(5000);
            http.setRequestMethod("GET");
            http.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(new InputStreamReader((InputStream) http.getContent()));
            JsonObject eObject = element.getAsJsonObject();

            int resStatus = eObject.get("status").getAsInt();

            if (resStatus == 300) {
                return null;
            }

            if (resStatus != 200) {
                System.out.println("[UpdaterException] [" + project + "] An error has occured. Our server responded, congratulations. He said: " + eObject.get("message").getAsString());
                return null;
            }

            JsonArray updates = eObject.get("updates").getAsJsonArray();
            for (JsonElement update : updates) {
                JsonObject updateObj = update.getAsJsonObject();
                String version = updateObj.get("version").getAsString();
                String download = updateObj.get("download").getAsString();
                boolean critical = updateObj.get("critical").getAsInt() != 0;
                return new Update(version, download, critical);
            }
            return null;
        } catch (Exception ex) {
            System.out.println("[UpdaterException] [" + project + "] An error has occured. Please report the following exception message to the developer of " + project);
            ex.printStackTrace();
            return null;
        }
    }

    private void downloadLatest(String fileURL, String project, Plugin pl) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("User-Agent", project + " UpdateChecker");
            int responseCode = http.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = http.getInputStream();
                String saveFilePath = new File(pl.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[4096];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();
            } else {
                System.out.println("[UpdaterException] [" + project + "] An error has occured while updating " + project + ". Please report the following exception message to the developer of " + project + " (resp. code: " + responseCode + ")");
            }

            http.disconnect();
        } catch (Exception ex) {
            System.out.println("[UpdaterException] [" + project + "] An error has occured. Please report the following exception message to the developer of " + project);
            ex.printStackTrace();
        }
    }



    public class Update {

        private String version, downloadlink;
        private boolean critical;

        public Update(String version, String downloadlink, boolean critical) {
            this.version = version;
            this.downloadlink = downloadlink;
            this.critical = critical;
        }

        public String getVersion() {
            return version;
        }

        public String getDownloadLink() {
            return downloadlink;
        }

        public boolean isCritical() {
            return critical;
        }
    }
}
