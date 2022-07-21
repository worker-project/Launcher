package com.workerai.launcher.database;

import com.workerai.launcher.App;
import com.workerai.launcher.savers.AccountSaver;

public class Requests {

    public static void initRequests() {
        Database.initDatabase();
    }

    public static void addAccount(String username, String uuid, String clientToken, String accessToken) {
        Account account = Requests.getAccount(uuid);

        if (account == null) {
            account = new Account();
            account.setUsername(username);
            account.setUuid(uuid);
            account.setClientToken(clientToken);
            account.setAccessToken(accessToken);

            Database.addAccount(account);
            App.getInstance().getLogger().debug(uuid + " added.");
        }

        AccountSaver.setCurrentAccount(account);
    }

    public static Account getAccount(String uuid) {
        App.getInstance().getLogger().debug("Searching " + uuid + " in SQLite...");
        Account account = Database.getAccount(uuid);

        if (account == null) {
            App.getInstance().getLogger().debug(uuid + " doesn't exist.");
            return null;
        }
        App.getInstance().getLogger().debug(uuid + " exist.");
        return account;
    }

    public static void removeAccount(String uuid) {
        Account account = Requests.getAccount(uuid);

        if (account == null) {
            return;
        }

        Database.removeAccount(account.getUuid());
        App.getInstance().getLogger().debug(uuid + " removed.");
    }
}
