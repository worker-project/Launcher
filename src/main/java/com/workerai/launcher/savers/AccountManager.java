package com.workerai.launcher.savers;

import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.database.authentication.ModuleResponse;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private static final List<Account> localAccounts = new ArrayList<>();
    private static Account currentAccount = null;

    public static void initLocalAccounts() {
        for (Account account : Requests.getRemoteAccounts()) {
            account.setResponse(ModuleResponse.getUserFromUuid(account.getUuid()));
            AccountManager.addLocalAccount(account);
        }
    }

    public static List<Account> getLocalAccounts() {
        return localAccounts;
    }

    public static Account getLocalAccount(Account account) {
        int accountIndex = localAccounts.indexOf(account);
        return localAccounts.get(accountIndex);
    }
    public static void addLocalAccount(Account account) {
        localAccounts.add(account);
    }
    public static void removeLocalAccount(Account account) {
        localAccounts.remove(account);
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }
    public static Account getCurrentAccount() {
        return currentAccount;
    }
    public static void removeCurrentAccount() { currentAccount = null; }

    public static Account getDebugAccount() {
        return Account.createAccount(null, null, null, null, new ModuleResponse(false, false));
    }
}
