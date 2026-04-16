package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestZipcode03 {

	private static String driver = "oracle.jdbc.OracleDriver";
	private static String dburl  = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String dbuid  = "hr";
	private static String dbpwd  = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(dburl, dbuid, dbpwd);
		
		Statement  stmt = conn.createStatement();
		String     sql  = "SELECT NVL(d.DEPARTMENT_NAME, '부서없음') DEPARTMENT_NAME, "                
					    + "e.FIRST_NAME, "
					    + "e.PHONE_NUMBER " 
						+ "FROM EMPLOYEES e JOIN DEPARTMENTS d ON "
						+ "e.DEPARTMENT_ID = d.DEPARTMENT_ID";
		
		ResultSet  rs   = stmt.executeQuery(sql);
		
		while( rs.next() != false ) {
			System.out.print(rs.getString(1) + ",");
			System.out.print(rs.getString(2) + ",");
			System.out.print(rs.getString(3));
			System.out.println();
		
		}
		rs.close();
		stmt.close();
		conn.close();
	}
}
