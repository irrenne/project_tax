package com.epam.tax.database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

    private static DBManager instance;
    private static DataSource dataSource;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }


    public Connection getConnection() throws SQLException {
        try {
            if (dataSource == null) {
                InitialContext initialContext = new InitialContext();
                dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/mysql");
            }

        } catch (NamingException e) {
            e.printStackTrace();
        }

        return dataSource.getConnection();
    }

}


//    public Connection getConnection() throws SQLException {
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schema_tax", "root", "_one_look1");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }