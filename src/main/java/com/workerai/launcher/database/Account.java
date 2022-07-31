package com.workerai.launcher.database;

public class Account {
    private static class AccountData {
        private String UUID;
        private String USERNAME;
        private String CLIENT_TOKEN;
        private String ACCESS_TOKEN;
    }

    public AccountData accountData = new AccountData();

    public String getUuid() {
        return accountData.UUID;
    }

    public void setUuid(String uuid) {
        accountData.UUID = uuid;
    }

    public String getUsername() {
        return accountData.USERNAME;
    }

    public void setUsername(String username) {
        accountData.USERNAME = username;
    }

    public String getClientToken() {
        return accountData.CLIENT_TOKEN;
    }

    public void setClientToken(String clientToken) {
        accountData.CLIENT_TOKEN = clientToken;
    }

    public String getAccessToken() {
        return accountData.ACCESS_TOKEN;
    }

    public void setAccessToken(String accessToken) {
        accountData.ACCESS_TOKEN = accessToken;
    }

}