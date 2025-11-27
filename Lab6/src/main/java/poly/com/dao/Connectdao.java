package poly.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectdao
{
	protected static Connection conn;
	private static final String URL = "jdbc:sqlserver://chez1s.duckdns.org:1433;databaseName=ABCNews;encrypt=false;trustServerCertificate=true;";
	private static final String USER = "sa";
	private static final String PASS = "123"; // pass cậu đặt ở docker

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
