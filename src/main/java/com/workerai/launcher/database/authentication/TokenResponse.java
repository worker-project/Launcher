package com.workerai.launcher.database.authentication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.workerai.launcher.WorkerLauncher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TokenResponse {
    public TokenResponse() {

    }

    public String getTokenFromUuid(String uuid) {
        try {
            URL url = new URL(String.format("http://185.245.183.191:2929/Unf6gWbunD2xDFyr?&uuid=%s", uuid));
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

            return obj.get("token").getAsString();
        } catch (IOException e) {
            WorkerLauncher.getInstance().getLogger().err("Remote SQLite doesn't respond! Returning default values...");
            return null;
        }
    }
}
