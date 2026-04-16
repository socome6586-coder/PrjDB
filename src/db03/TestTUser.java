package db03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestTUser {
	// 연결 문자열
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url    = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid  = "sky";
	private static String dbpwd  = "1234";
	
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// CRUD 예제 Create, Read, Update, delete
		do {
			//화면출력
			System.out.println("============================");
			System.out.println("          회원 정보         ");
			System.out.println("============================");
			System.out.println("1. 회원목록");
			System.out.println("2. 회원조회");
			System.out.println("3. 회원추가");
			System.out.println("4. 회원수정");
			System.out.println("5. 회원삭제");
			System.out.println("Q. 종료");
			
			System.out.println("선택:");
			String choice = in.nextLine();
			
			TUserDTO tuser = null;
			
			switch(choice) {
			case "1" : //회원목록
				ArrayList<TUserDTO> userList = getTUserList();
				displayList(userList);
				break;
			case "2" : //회원조회 (아이디)
				System.out.println("조회할 아이디를 입력하세요.");
				String uid = in.nextLine();
				tuser      = getTUser(uid);
				display(tuser);
				break;
			case "3" : //회원추가
				tuser           = inputData();
				int      aftcnt = addTuser(tuser);
				System.out.println(aftcnt + "건 저장되었습니다.");
				break;
			case "4" : //회원수정
				
				break;
			case "5" : //회원삭제
				break;
			case "Q" : //종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
				break;
			}
			
			
		} while(true); // 무한반복 : 무한루프
		
		
	}

	// 전체 목록을 출력
	private static void displayList(ArrayList<TUserDTO> userList) {
		if(userList.size() == 0) {
			System.out.println("조회한 자료가 없습니다");
			return;
		}
		
		String fmt = "";
		String msg = "";
		for (TUserDTO tuser : userList) {
			String userid   = tuser.getUserid();
			String username = tuser.getUsername();
			String email    = tuser.getEmail();
			msg = """
			%s %s %s
			""".formatted(userid, username, email); // java template 문자열
			System.out.print(msg);
		}
		System.out.println("==========Press Enter Key==========");
		in.nextLine();
	}

	// 1. 전체 목록 조회 - db에서
	private static ArrayList<TUserDTO> getTUserList() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		Connection         conn   = DriverManager.getConnection(url, dbuid, dbpwd);
		String             sql    = " SELECT * FROM TUSER ";
		sql                      += " ORDER BY USERID ASC ";
		PreparedStatement  pstmt  = conn.prepareStatement(sql);
		ResultSet          rs     = pstmt.executeQuery();
		
		ArrayList<TUserDTO> userList = new ArrayList<>();
		
		while( rs.next() ) {
			String   userid   = rs.getString("userid");
			String   username = rs.getString("username");
			String   email    = rs.getString("email");
			TUserDTO tuser    = new TUserDTO(userid, username, email);
			userList.add(tuser);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return userList;
	}

	// 2. 입력받은 아이디로 한 줄을 db 에서 조회한다
	private static TUserDTO getTUser(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection         conn   = DriverManager.getConnection(url, dbuid, dbpwd);
		String             sql    = " SELECT * FROM TUSER WHERE USERID = ? ";
		PreparedStatement  pstmt  = conn.prepareStatement(sql);
		pstmt.setString(1, uid.toUpperCase());
		
		ResultSet          rs     = pstmt.executeQuery();
		TUserDTO tuser = null;
		if ( rs.next() ) { // 해당 자료가 있다
			String userid   = rs.getString("userid");
			String username = rs.getString("username");
			String email    = rs.getString("email");
			tuser           = new TUserDTO(userid, username, email);
			
		} else { // 해당 자료가 없다 : primary key
			
		}
		
		pstmt.close();
		conn.close();
		
		return tuser;
	}

	// TUser 한 줄을 출력한다
	private static void display(TUserDTO tuser) {
		if(tuser == null ) 
			System.out.println("조회한 자료가 없습니다.");
		else {
			String msg = String.format("%s %s %s", 
					tuser.getUserid(), tuser.getUsername(), tuser.getEmail());
			System.out.println(msg);
		}
	}

	// 3. db 에 insert 한다
	private static int addTuser(TUserDTO tuser) throws ClassNotFoundException, SQLException {
		
		Class.forName(driver);
		Connection			conn   = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String 				sql    = "";
		sql += " INSERT INTO TUSER VALUES (?, ?, ?) ";
		PreparedStatement   pstmt  = conn.prepareStatement(sql);
		pstmt.setString(1, tuser.getUserid());
		pstmt.setString(2, tuser.getUsername());
		pstmt.setString(3, tuser.getEmail());
		
		int 				aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		return aftcnt;
	}

	// 데이터를 키보드로 입력 받는다 
	private static TUserDTO inputData() {
		System.out.println("아이디:");
		String userid   = in.nextLine();
		System.out.println("이름:");
		String username = in.nextLine();
		System.out.println("이메일:");
		String email    = in.nextLine();
		
		TUserDTO tuser = new TUserDTO(userid, username, email);
		return   tuser;
	}
}
