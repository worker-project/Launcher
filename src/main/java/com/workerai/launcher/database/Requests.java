package com.workerai.launcher.database;

import com.workerai.launcher.App;
import com.workerai.launcher.savers.AccountSaver;

import java.util.List;

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
            AccountSaver.addAccount(account);
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

    public static List<Account> getAccounts() {
        App.getInstance().getLogger().debug("Searching all accounts in SQLite...");
        List<Account> accountList = Database.getAccounts();

        if(accountList == null) {
            App.getInstance().getLogger().debug("No account found in SQLite.");
            return null;
        }

        for (Account account : accountList) {
            App.getInstance().getLogger().debug("Found " + account.getUuid() + " in SQLite.");
        }
        return accountList;
    }

    public static void removeAccount(String uuid) {
        Account account = Requests.getAccount(uuid);

        if (account == null) return;

        Database.removeAccount(account.getUuid());
        App.getInstance().getLogger().debug(uuid + " removed.");
    }
}
