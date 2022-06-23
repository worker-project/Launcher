package com.workerai.launcher.database;

import com.workerai.launcher.App;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public abstract class SQLite {
    private final String databaseName;
    private final String databaseTable;
    private final File databaseFolder;

    private Connection connection;

    protected SQLite(String databaseName, String databaseTable) {
        this.databaseName = databaseName;
        this.databaseTable = databaseTable;

        databaseFolder = new File(App.getInstance().getLauncherDirectory().toFile(), databaseName + ".sqlite");
        if (!databaseFolder.exists()) {
            try {
                databaseFolder.createNewFile();
                App.getInstance().getLogger().info("Successfully created local SQLite database!");
            } catch (IOException e) {
                App.getInstance().getLogger().err("Failed creating local SQLite database!");
            }
        }
    }

    public Connection openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFolder);
            App.getInstance().getLogger().debug("Successfully opened local SQLite connection.");
        } catch (SQLException ex) {
            App.getInstance().getLogger().err("Failed opening local SQLite connection!");
        }

        try {
            Statement s = connection.createStatement();
            s.executeUpdate(databaseTable);
            s.close();
            App.getInstance().getLogger().info("Successfully tested local SQLite connection!");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            App.getInstance().getLogger().err("Failed testing local SQLite connection!");
            return null;
        }
    }

    public void closeConnection(Object obj) {
        String className = obj.getClass().getSimpleName();
        try {
            if(className.contains("PreparedStatement")) {
                PreparedStatement ps = (PreparedStatement) obj;
                ps.close();
            } else if(className.contains("ResultSet")) {
                ResultSet rs = (ResultSet) obj;
                rs.close();
            } else if(className.contains("Connection")) {
                Connection conn = (Connection) obj;
                App.getInstance().getLogger().debug("Successfully closed opened local SQLite connection.");
                conn.close();
            } else {
                App.getInstance().getLogger().err("Failed closing local SQLite connection!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public abstract void createStatement(String username, String uuid, String clientToken, String accessToken, Boolean remember);
    public abstract boolean getStatement(String uuid);
    public abstract boolean removeStatement(String uuid);

    public String getDatabaseName() { return databaseName; }
}
