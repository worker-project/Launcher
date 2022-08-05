package com.workerai.launcher.database;

public class Account {
    private String UUID;
    private String USERNAME;
    private String CLIENT_TOKEN;
    private String ACCESS_TOKEN;
    private Response RESPONSE;

    public static Account createAccount(String username, String uuid, String clientToken, String accessToken, Response response) {
        Account account = new Account();

        account.USERNAME = username;
        account.UUID = uuid;
        account.CLIENT_TOKEN = clientToken;
        account.ACCESS_TOKEN = accessToken;
        account.RESPONSE = response;

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

    public void setResponse(Response response) {
        RESPONSE = response;
    }
    public Response getResponse() {
        return RESPONSE;
    }
}
