package com.workerai.launcher.savers;

import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Requests;
import com.workerai.launcher.database.Response;

import java.util.ArrayList;
import java.util.List;

public class AccountSaver {
    private static final List<Account> accounts = new ArrayList<>();
    private static Account currentAccount = null;
    private static Response currentResponse = null;

    public static void initAccounts() {
        for (Account account : Requests.getAccounts()) {
            AccountSaver.addAccount(account);
        }
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static void removeAccount(Account account) {
        accounts.remove(account);
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
    }

    public static Account getCurrentAccount() {
        if(currentAccount != null) {
            return currentAccount;
        }
        Account account = new Account();
        return account.getDebugAccount();
    }

    public static Response getCurrentResponse() {
        if(currentResponse != null) {
            return currentResponse;
        }
        return Response.getResponse(getCurrentAccount().getUuid());
    }

    public static void removeCurrentAccount() { currentAccount = null; }
}
