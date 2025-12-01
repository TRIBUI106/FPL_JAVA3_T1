package poly.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectdao {
    private static final String URL = "jdbc:mariadb://yeume-enterprise.edu.vn:3306/yeumeent_dihoc";
    private static final String USER = "yeumeent_dihoc";
    private static final String PASS = "Caigivaytroi@123";

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Driver MariaDB loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}