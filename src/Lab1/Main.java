package Lab1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

	static String driver = "com.mysql.cj.jdbc.Driver"; 
	static String url = "jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false"; 
	static String user = "root"; 
	static String password = "123456"; 
    static Connection conn = null;
    static Statement stat = null;
    
    public static void connect() {
	    try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password); 
			stat = conn.createStatement();
		} catch (Exception e) {
			System.out.println("数据库连接异常！");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		connect();
		new Window(stat);
	}
}
