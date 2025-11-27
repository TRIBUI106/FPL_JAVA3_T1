package poly.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectdao
{
	protected static Connection conn;
	private static final String URL = "jdbc:mariadb://yeume-enterprise.edu.vn:3306;databaseName=yeumeent_dihoc";
	private static final String USER = "yeumeent_dihoc";
	private static final String PASS = "Caigivaytroi@123"; // pass cậu đặt ở docker

	public static Connection getConnection() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(URL, USER, PASS);
	}

	 public Connectdao() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(URL, USER, PASS);
	        System.out.println("Kết nối MySQL thành công!");
	    	} 
	    catch (Exception ex)
	     {
	        ex.printStackTrace();
	    	}
		}
	    
}
