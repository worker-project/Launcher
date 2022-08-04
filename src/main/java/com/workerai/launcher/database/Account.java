package com.workerai.launcher.database;

public class Account {
    private static class AccountData {
        private String UUID;
        private String USERNAME;
        private String DISCORD;
        private String CLIENT_TOKEN;
        private String ACCESS_TOKEN;
    }

    public Account getDebugAccount() {
        Account debugAccount = new Account();
        debugAccount.accountData.UUID = "null";
        debugAccount.accountData.USERNAME = "null";
        debugAccount.accountData.DISCORD = "null";
        debugAccount.accountData.ACCESS_TOKEN = null;
        debugAccount.accountData.CLIENT_TOKEN = null;
        return debugAccount;
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

    public void setDiscord(String discord) { accountData.DISCORD = discord; }

    public String getDiscord() { return accountData.DISCORD; }
}
