package com.workerai.launcher.database;

import com.workerai.launcher.database.authentication.ModuleResponse;

public class Account {
    private String UUID;
    private String USERNAME;
    private String CLIENT_TOKEN;
    private String ACCESS_TOKEN;
    private ModuleResponse MODULE_RESPONSE;

    public static Account createAccount(String username, String uuid, String clientToken, String accessToken, ModuleResponse moduleResponse) {
        Account account = new Account();

        account.USERNAME = username;
        account.UUID = uuid;
        account.CLIENT_TOKEN = clientToken;
        account.ACCESS_TOKEN = accessToken;
        account.MODULE_RESPONSE = moduleResponse;

        return account;
    }

    public String getUuid() {
        return UUID;
    }

    public void setUuid(String uuid) {
        UUID = uuid;
    }

    public String getUsername() {
        return USERNAME;
    }

    public void setUsername(String username) {
        USERNAME = username;
    }

    public String getClientToken() {
        return CLIENT_TOKEN;
    }

    public void setClientToken(String clientToken) {
        CLIENT_TOKEN = clientToken;
    }

    public String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }

    public void setResponse(ModuleResponse moduleResponse) {
        MODULE_RESPONSE = moduleResponse;
    }
    public ModuleResponse getResponse() {
        return MODULE_RESPONSE;
    }
}
