package db03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
			
			switch(choice) {
			case "1" : //회원목록 
				break;
			case "2" : //회원조회
				break;
			case "3" : //회원추가
				TUserDTO tuser  = inputData();
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

	// db 에 insert 한다
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
