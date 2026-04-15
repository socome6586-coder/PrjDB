package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestZipcode02 {

	private static String driver = "oracle.jdbc.OracleDriver";
	private static String dburl  = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static String dbuid  = "sky";
	private static String dbpwd  = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(dburl, dbuid, dbpwd);
		
		Statement  stmt = conn.createStatement();
		String     sql  = "SELECT ZIPCODE, SIDO, GUGUN, DONG, nvl(BUNJI, ' ') bj, SEQ "
						+ " FROM ZIPCODE "
						+ " WHERE DONG LIKE '%롯데백화점%' ";
		System.out.println(sql);
		ResultSet  rs   = stmt.executeQuery(sql);
		
		while( rs.next() != false ) {
			System.out.print(rs.getString(1) + ",");
			System.out.print(rs.getString(2) + ",");
			System.out.print(rs.getString(3) + ",");
			System.out.print(rs.getString(4) + ",");
			System.out.print(rs.getString(5) + ",");
			System.out.print(rs.getInt(6));
			System.out.println();
			/*
			System.out.print(rs.getString("zipcode") + ",");
			System.out.print(rs.getString("sido") + ",");
			System.out.print(rs.getString("gugun") + ",");
			System.out.print(rs.getString("dong") + ",");
			System.out.print(rs.getString("bj") + ",");
			System.out.print(rs.getInt("seq") + ",");
			System.out.println();
			*/
		}
		
		rs.close();
		stmt.close();
		conn.close();
	}

}
