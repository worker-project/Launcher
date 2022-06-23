package com.workerai.launcher.database;

public class Account {
    private final int ID;
    private final String UUID;
    private final String USERNAME;
    private final String CLIENT_TOKEN;
    private final String ACCESS_TOKEN;
    private final boolean REMEMBER;

    public Account(int id, String uuid, String username, String clientToken, String accessToken, Boolean remember) {
        this.ID = id;
        this.UUID = uuid;
        this.USERNAME = username;
        this.CLIENT_TOKEN = clientToken;
        this.ACCESS_TOKEN = accessToken;
        this.REMEMBER = remember;
    }

    public int getId() {
        return this.ID;
    }

    public String getUuid() {
        return this.UUID;
    }

    public String getUsername() {
        return this.USERNAME;
    }

    public String getClientToken() {
        return this.CLIENT_TOKEN;
    }

    public String getAccessToken() {
        return this.ACCESS_TOKEN;
    }

    public Boolean getRemember() {
        return this.REMEMBER;
    }
}
