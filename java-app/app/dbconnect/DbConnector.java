package semestral_project.app.dbconnect;

import java.sql.*;


/**
 * Třída pro přístup k databázi.
 */
public class DbConnector {
    private final String url = "jdbc:sqlserver://147.230.21.34;databaseName=DBS2020_BrunoPfohl";
    private final String uName = "student";
    private final String password = "student";

    private Connection conn;

    private static DbConnector instance;

    public static DbConnector instance () {
        if (instance == null) {
            DbConnector.instance = new DbConnector();

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                if (!IsConnected()){
                    instance.conn = DriverManager.getConnection(instance.url, instance.uName, instance.password);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }


    /**
     * Vrací, jestli je připojení na databázi v pořádku.
     */
    public static boolean IsConnected() {
        boolean result = false;

        try {
            result = instance() != null && instance.conn != null && !instance.conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public PreparedStatement prepare(String sql) {
        PreparedStatement result = null;

        try {
            result = this.conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
