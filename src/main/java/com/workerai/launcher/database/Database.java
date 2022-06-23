package com.workerai.launcher.database;

import com.workerai.launcher.App;
import com.workerai.launcher.savers.AccountSaver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database extends SQLite {
    private static final String databaseTable = "CREATE TABLE IF NOT EXISTS accounts (" +
            " ID            INTEGER     PRIMARY KEY AUTOINCREMENT," +
            " USERNAME      CHAR(16)    NOT NULL, " +
            " UUID          CHAR(36)    NOT NULL, " +
            " CLIENT_TOKEN  CHAR(255)   NOT NULL, " +
            " ACCESS_TOKEN  CHAR(255)   NOT NULL," +
            " REMEMBER      BOOLEAN     NOT NULL" +
            ");";

    public Database(String databaseName) {
        super(databaseName, databaseTable);
    }

    @Override
    public void createStatement(String username, String uuid, String clientToken, String accessToken, Boolean remember) {
        int REGISTER_STATE = 1;
        Connection conn = null;
        PreparedStatement ps = null;

        boolean statementExist = getStatement(uuid);

        try {
            conn = openConnection();

            if(!statementExist) {
                ps = conn.prepareStatement("REPLACE INTO " + getDatabaseName() + " (USERNAME,UUID,CLIENT_TOKEN,ACCESS_TOKEN,REMEMBER) VALUES(?,?,?,?,?)");
            } else {
                ps = conn.prepareStatement("UPDATE " + getDatabaseName() + " SET USERNAME = ?, CLIENT_TOKEN = ?, ACCESS_TOKEN = ?, REMEMBER = ? WHERE UUID = ?");
            }
            ps.setString(1, username); REGISTER_STATE++;
            ps.setString(2, uuid); REGISTER_STATE++;
            ps.setString(3, clientToken); REGISTER_STATE++;
            ps.setString(4, accessToken); REGISTER_STATE++;
            ps.setBoolean(5, remember); REGISTER_STATE++;
            ps.executeUpdate();
            App.getInstance().getLogger().info("Successfully executed SQLite statements");

            ps = conn.prepareStatement("SELECT ID FROM " + getDatabaseName() + " WHERE UUID = ?");
            ps.setString(1, uuid);
            if(!statementExist) App.getInstance().getAccountManager().addAccount(ps.executeQuery().getInt("ID"), uuid, username, clientToken, accessToken, remember);
            AccountSaver.currentAccount = new Account(ps.executeQuery().getInt("ID"), uuid, username, clientToken, accessToken, remember);
        } catch (SQLException ex) {
            App.getInstance().getLogger().err("Failed executing SQLite statement at state: " + REGISTER_STATE);
        } finally {
            if(ps != null) closeConnection(ps);
            if(conn != null) closeConnection(conn);
        }
    }

    @Override
    public boolean getStatement(String uuid) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = openConnection();

            ps = conn.prepareStatement("SELECT * FROM " + getDatabaseName() + " WHERE UUID = ?");
            ps.setString(1, uuid);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString("UUID").equals(uuid)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            App.getInstance().getLogger().err("Failed testing account in local SQLite database!");
        } finally {
            if(ps != null) closeConnection(ps);
            if(rs != null) closeConnection(rs);
            if(conn != null) closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean removeStatement(String uuid) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = openConnection();

            ps = conn.prepareStatement("DELETE FROM " + getDatabaseName() + " WHERE UUID = ?;");
            ps.setString(1, uuid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            App.getInstance().getLogger().err("Failed deleting account in local SQLite database!");
        } finally {
            if(ps != null) closeConnection(ps);
            if(conn != null) closeConnection(conn);
        }
        return false;
    }
}
