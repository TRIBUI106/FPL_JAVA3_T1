package poly.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectdao
{
	 protected static Connection conn;
	   
	 public Connectdao() {
	    try {
	        String url = "jdbc:mysql://localhost:3306/lab6_java3_csdl?useUnicode=true&characterEncoding=UTF-8";
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, "root", "");
	        System.out.println("Kết nối MySQL thành công!");
	    	} 
	    catch (Exception ex)
	     {
	        ex.printStackTrace();
	    	}
		}
	    
}
