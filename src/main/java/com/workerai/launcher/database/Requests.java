package com.workerai.launcher.database;

import com.workerai.launcher.WorkerLauncher;
import com.workerai.launcher.savers.AccountManager;

import java.util.List;

public class Requests {

    public static void initRequests() {
        Database.initDatabase();
    }

    public static void addRemoteAccount(Account account) {
        Account acc = Requests.getRemoteAccount(account.getUuid());

        if (acc == null) {
            acc = new Account();
            acc.setUsername(account.getUsername());
            acc.setUuid(account.getUuid());
            acc.setClientToken(account.getClientToken());
            acc.setAccessToken(account.getAccessToken());
            acc.setResponse(account.getResponse());

            Database.addRemoteAccount(acc);
            AccountManager.addLocalAccount(acc);
            WorkerLauncher.getInstance().getLogger().debug(acc.getUuid() + " added.");
        }
    }

    public static Account getRemoteAccount(String uuid) {
        WorkerLauncher.getInstance().getLogger().debug("Searching " + uuid + " in SQLite...");
        Account account = Database.getRemoteAccount(uuid);

        if (account == null) {
            WorkerLauncher.getInstance().getLogger().debug(uuid + " doesn't exist.");
            return null;
        }
        WorkerLauncher.getInstance().getLogger().debug(uuid + " exist.");
        return account;
    }

    public static List<Account> getRemoteAccounts() {
        WorkerLauncher.getInstance().getLogger().debug("Searching all accounts in SQLite...");
        List<Account> remoteAccounts = Database.getRemoteAccounts();

        if(remoteAccounts == null) {
            WorkerLauncher.getInstance().getLogger().debug("No account found in SQLite.");
            return null;
        }

        for (Account account : remoteAccounts) {
            WorkerLauncher.getInstance().getLogger().debug("Found " + account.getUuid() + " in SQLite.");
        }
        return remoteAccounts;
    }

    public static void removeRemoteAccount(String uuid) {
        Account account = Requests.getRemoteAccount(uuid);

        if (account == null) return;

        Database.removeRemoteAccount(account.getUuid());
        WorkerLauncher.getInstance().getLogger().debug(uuid + " removed.");
    }
}
