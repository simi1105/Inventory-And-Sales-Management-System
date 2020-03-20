package com.wipro.sales.util;
import java.sql.*;
public class DBUtil {
	public static Connection getDBConnection() {
		Connection con=null;
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String user="system";
		String password="asd1";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con=DriverManager.getConnection(url,user,password);
			return con;
		}
		catch(ClassNotFoundException e) { 
			e.printStackTrace(); 
			return null;}
		catch(SQLException e) { 
			e.printStackTrace(); 
			return null;}
		
	}
}
