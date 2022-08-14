package com.workerai.launcher.database.authentication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workerai.launcher.WorkerLauncher;
import fr.flowarg.flowupdater.download.json.Mod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModuleResponse {
    boolean automine, forage;

    public boolean hasAutomine() {
        return automine;
    }

    public void setAutomine(boolean automine) {
        this.automine = automine;
    }

    public boolean hasForage() {
        return forage;
    }

    public void setForage(boolean forage) {
        this.forage = forage;
    }

    public ModuleResponse(boolean automine, boolean forage) {
        this.setAutomine(automine);
        this.setForage(forage);
    }

    public ModuleResponse() {

    }

    public ModuleResponse getUserFromUuid(String uuid) {
        try {
            URL url = new URL(String.format("http://185.245.183.191:2929/jWuR0gHff54WvVzL?&uuid=%s", uuid));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject obj = (JsonObject) JsonParser.parseString(response.toString());

            if (!obj.get("exists").getAsBoolean()) {
                return null;
            }

            return new ModuleResponse(obj.get("automine").getAsBoolean(), obj.get("forage").getAsBoolean());
        } catch (IOException e) {
            WorkerLauncher.getInstance().getLogger().err("Remote SQLite doesn't respond! Returning default values...");
            return null;
        }
    }
}
