package com.workerai.launcher.utils;

import com.workerai.launcher.App;
import fr.theshark34.openlauncherlib.util.Saver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AccountManager {
    private final Saver saver;
    private List<String> accessToken = new ArrayList<>();
    private List<String> clientToken = new ArrayList<>();

    private String currClientToken;
    private String currAccessToken;

    public AccountManager(Saver saver) {
        this.saver = saver;
        this.saver.load();

        readAccountsFromSave();
    }

    private void readAccountsFromSave() {
        long lines = -2;
        try (BufferedReader reader = new BufferedReader(new FileReader(App.getInstance().getLauncherDirectory() + "\\accounts.save"))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < lines; i+=2) {
            clientToken.add(saver.get("ClientToken[" + i + "]"));
            accessToken.add(saver.get("AccessToken[" + i + "]"));
            System.out.println(clientToken.get(i));
            System.out.println(accessToken.get(i));
        }
    }

    public void updateClientToken(String token) {
        boolean foundToken = false;
        for (int i = 0; i < clientToken.size(); i++) {
            if(clientToken.get(i).equals(token)) {
                App.getInstance().getLogger().info("Updating ClientToken[" + i + "]");
                saver.set("ClientToken[" + i + "]", token);
                clientToken.set(i, token);
                foundToken = true;
            }
        }
        if(!foundToken) {
            App.getInstance().getLogger().info("Creating ClientToken[" + clientToken.size() + "]");
            saver.set("ClientToken[" + clientToken.size() + "]", token);
            clientToken.add(token);
            currClientToken = token;
        }
    }

    public void removeClientToken(String token) {
        App.getInstance().getLogger().info("Removing ClientToken");
        for (int i = 0; i < clientToken.size(); i++) {
            if(clientToken.get(i).equals(token)) {
                saver.remove("ClientToken[" + i + "]");
            }
        }
        clientToken.remove(token);
    }

    public void updateAccessToken(String token) {
        boolean foundToken = false;
        for (int i = 0; i < accessToken.size(); i++) {
            if(accessToken.get(i).contains(token)) {
                App.getInstance().getLogger().info("Updating AccessToken[" + i + "]");
                saver.set("AccessToken[" + i + "]", token);
                clientToken.set(i, token);
                foundToken = true;
            }
        }
        if(!foundToken) {
            App.getInstance().getLogger().info("Creating AccessToken[" + accessToken.size() + "]");
            saver.set("AccessToken[" + accessToken.size() + "]", token);
            accessToken.add(token);
            currAccessToken = token;
        }
    }

    public void removeAccessToken(String token) {
        App.getInstance().getLogger().info("Removing AccessToken");
        for (int i = 0; i < accessToken.size(); i++) {
            if(accessToken.get(i).equals(token)) {
                saver.remove("AccessToken[" + i + "]");
            }
        }
        accessToken.remove(token);
    }

    public void setCurrClientToken(String s) {
        currClientToken = s;
    }
    public void setCurrAccessToken(String s) {
        currAccessToken = s;
    }
    public String getCurrClientToken() {
        return currClientToken;
    }
    public String getCurrAccessToken() {
        return currAccessToken;
    }

    public Saver getSaver() {
        return this.saver;
    }
}
