package com.workerai.launcher.database;

import com.workerai.launcher.WorkerLauncher;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    static Connection conn = null;

    static void initDatabase() {
        try {
            String url = String.valueOf(new File(WorkerLauncher.getInstance().getLauncherFolder().toFile(), "data"));

            conn = DriverManager.getConnection("jdbc:sqlite:" + url);

            WorkerLauncher.getInstance().getLogger().debug("Connection to SQLite has been established.");

            String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
                    " ID            INTEGER     PRIMARY KEY," +
                    " USERNAME      CHAR(16)    NOT NULL, " +
                    " UUID          CHAR(36)    NOT NULL, " +
                    " CLIENT_TOKEN  CHAR(255)   NOT NULL, " +
                    " ACCESS_TOKEN  CHAR(255)   NOT NULL" +
                    ");";

            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void addRemoteAccount(Account account) {
        try {
            String sql = "INSERT INTO accounts (USERNAME, UUID, CLIENT_TOKEN, ACCESS_TOKEN) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getUuid());
            stmt.setString(3, account.getClientToken());
            stmt.setString(4, account.getAccessToken());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void removeRemoteAccount(String uuid) {
        try {
            String sql = "DELETE FROM accounts WHERE UUID = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, uuid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static Account getRemoteAccount(String uuid) {
        try {
            String sql = "SELECT * FROM accounts WHERE UUID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            Account account = new Account();
            account.setUsername(rs.getString("USERNAME"));
            account.setUuid(rs.getString("UUID"));
            account.setClientToken(rs.getString("CLIENT_TOKEN"));
            account.setAccessToken(rs.getString("ACCESS_TOKEN"));
            return account;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    static List<Account> getRemoteAccounts() {
        try {
            String sql = "SELECT * FROM accounts";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<Account> remoteAccounts = new ArrayList<>();

            while (rs.next()) {
                Account account = new Account();
                account.setUsername(rs.getString("USERNAME"));
                account.setUuid(rs.getString("UUID"));
                account.setClientToken(rs.getString("CLIENT_TOKEN"));
                account.setAccessToken(rs.getString("ACCESS_TOKEN"));
                remoteAccounts.add(account);
            }

            return remoteAccounts;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
