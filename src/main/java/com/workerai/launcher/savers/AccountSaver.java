package com.workerai.launcher.savers;

import com.workerai.launcher.App;
import com.workerai.launcher.database.Account;
import com.workerai.launcher.database.Database;
import com.workerai.launcher.database.SQLite;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountSaver {
    private final SQLite database;
    private final List<Account> accounts = new ArrayList<>();

    public static Account currentAccount = null;

    private GridPane accountsPane;

    public AccountSaver(String databaseName) {
        database = new Database(databaseName);

        ResultSet rs = null;
        try {
            Connection conn = database.openConnection();
            rs = conn.prepareStatement("SELECT * FROM " + databaseName).executeQuery();
            while (rs.next()) {
                addAccount(rs.getInt("ID"),
                        rs.getString("UUID"),
                        rs.getString("USERNAME"),
                        rs.getString("CLIENT_TOKEN"),
                        rs.getString("ACCESS_TOKEN"),
                        rs.getBoolean("REMEMBER")
                );
            }
            database.closeConnection(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null) database.closeConnection(rs);
        }
    }

    public void addAccount(int id, String uuid, String username, String clientToken, String accessToken, Boolean autoAuth) {
        accounts.add(new Account(id, uuid, username, clientToken, accessToken, autoAuth));
        App.getInstance().getLogger().warn("Added account to local cache!");
    }

    public void removeAccount(int id) {
        accounts.forEach(account -> {
            if (account.getId() == id) {
                accounts.remove(account);
                database.removeStatement(String.valueOf(account.getUuid()));
                App.getInstance().getLogger().warn("Removed account from local cache and SQLite database!");
            }
        });
    }

    public Account getAccount(int id) {
        for(Account account : accounts) {
            if (account.getId() == id) return account;
        } throw new NullPointerException("Failed retrieving account!");
    }

    public void showAccount() {
        for(Account account : accounts) {
            System.out.println("========= ACCOUNT ==========");
            System.out.println("ACCOUNT ID = "+ account.getId());
            System.out.println("ACCOUNT UUID = "+ account.getUuid());
            System.out.println("ACCOUNT USERNAME = "+ account.getUsername());
            System.out.println("ACCOUNT CLIENT = "+ account.getClientToken());
            System.out.println("ACCOUNT ACCESS = "+ account.getAccessToken());
            System.out.println("ACCOUNT AUTH = "+ account.getRemember());
            System.out.println("============================");
        }
    }

    public List<Account> getAccounts() { return accounts; }

    public SQLite getDatabase() {
        return database;
    }
}
