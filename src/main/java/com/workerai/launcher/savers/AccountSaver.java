package com.workerai.launcher.savers;

import com.workerai.launcher.database.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountSaver {
    private static final List<Account> accounts = new ArrayList<>();
    private static Account currentAccount = null;

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;
        AccountSaver.addAccount(account);
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }
}
