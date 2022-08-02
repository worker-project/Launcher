package com.workerai.launcher.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workerai.launcher.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Response {
    public static class AccountModules {
        private boolean automine;
        private boolean forage;

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
    }

    private final AccountModules accountModules = new AccountModules();

    public Response(boolean automine, boolean forage) {
        this.accountModules.setAutomine(automine);
        this.accountModules.setForage(forage);
    }

    public static Response getResponse(String uuid) {
        try {
            URL url = new URL(String.format("http://localhost:2929/jWuR0gHff54WvVzL?&uuid=%s", uuid));
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
                return new Response(false, false);
            }

            return new Response(obj.get("automine").getAsBoolean(), obj.get("forage").getAsBoolean());
        } catch (IOException e) {
            App.getInstance().getLogger().err("Remote SQLite doesn't respond! Returning default values...");
            return new Response(false, false);
        }
    }

    public boolean hasAutomine() {
        return accountModules.hasAutomine();
    }

    public boolean hasForage() {
        return accountModules.hasForage();
    }
}
