package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

//DAO : (Data Access Object) : DB에 직접 접근해서 사용자의 요청에 맞는 sql문 실행 후 결과 반환(=> JDBC 사용)

public class MemberDao {
	private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USER_NAME = "C##JDBC";
	private final String PASSWORD = "JDBC";
	/*
	 * * JDBC용 객체
	 * - Connection : DB연결정보를 담고있는 객체
	 * - Statement : 연결된 DB에 sql문을 전달해서 실행하고 결과를 받아주는 객체
	 * - ResultSet : SELECT문(DQL) 실행 후 조회된 결과물으 담고있는 객체
	 * 
	 * JDBC 과정 (순서*)
	 * [1] jdbc driver 등록 : 사용할 DBMS(오라클)에서 제공하는 클래스 등록
	 * [2] Connection 객체 생성 : DB정보 (url, 사용자명, 비밀번호)를 통해 해당 DB와 연결하면서 생성
	 * [3] Statement 객체 생성 : Connection 객체를 이용하여 생성. sql문을 실행하고 결과를 받아줄것임
	 * [4] sql문 전달해서 실행 후 결과 받기 
	 * 	- SELECT문 실행시 ResultSet 객체로 조회 결과를 받음
	 * 	- DML(INSERT/UPDATE/DELETE) 실행 시 int 타입으로 처리 결과를 받음 (처리된 행 수)
	 * [5] 결과에 대한 처리
	 * 	- ResultSet 객체에서 데이터를 하나씩 추출하여 vo객체로 옮겨담기(저장)
	 * 	- DML의 경우 트랜잭션 처리 (성공했을떄는 commit, 싪패했을때는 rollback)
	 * [6] 자원반납 (close) => 생성 역순으로!!!
	 */
	
	/**
	 * 사용자가 입력한 정보들을 DB에 추가하는 메소드 (=> 회원 정보 추가)
	 * 
	 * @param m 사용자가 입력한 값들이 담겨있는 Member 객체
	 * @return insert문 실행 후 처리된 행 수
	 */
	public int insertMember(Member m) {
		//insert문 --> int (처리된 행 수) --> 트랜잭션 처리
		int result = 0;
		/*
		String sql = "INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL, "
				+ "'" + m.getUserId() + "', " //'user01',
				+ "'" + m.getUserPw() + "', " //'pass01',
				+ "'" + m.getUserName() + "', "//'아이유',
				+ "'" + m.getGender() + "', "
					  + m.getAge() + ","		//20,
				+ "'" + m.getEmail() + "', "
				+ "'" + m.getAddress() + "', "
				+ "'" + m.getPhone() + "', "
				+ "'" + m.getHobby() + "', SYSDATE)";
		*/
		String sql = "INSERT INTO MEMBER VALUES (SEQ_USERNO.NEXTVAL, "
							+"?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		System.out.println("-------------------------");
		System.out.println(sql);
		System.out.println("-------------------------");
		
		//JDBC용 객체 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			//1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2) Connection 객체 생성 => DB연결
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);
			//3) statement객체 생성
			//stmt = conn.createStatement();
			//3')PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql); //객체 생성 시 sql문을 정달(미완성/완성 형태 상관없이 전달 가능)
			//set xxx(xxx: 데이터 ㅌ입) --> setxxx(물음표 순서, 전달할 값(변수))
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPw());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, Character.valueOf(m.getGender()).toString());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getAddress());
			pstmt.setString(8, m.getPhone());
			pstmt.setString(9, m.getHobby());
			
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		//2) Connection 객체 생성 =>DB 연결
		
		return result;
		
	}

	public ArrayList<Member> selectList() {
		//SELECT문 (여러행 조회 --> ResultSet 객체 --> ArrayList<Member>에 담기
		ArrayList<Member> list = new ArrayList<>();		//리스트가 비어있는 상태 []
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		//실행할 sql문
		String sql = "SELECT * FROM MEMBER";
		
		
		try {
		//1) JDBC 드라이버 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		//2) Connection 객체 생성
		conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		
		//3) Statement 객체 생성
		stmt = conn.createStatement();
		
		//4)쿼리 실행 후 결과 받기
		rset = stmt.executeQuery(sql);
		
		//5)
		while(rset.next()) { //next():데이터가 있을경우 true
			Member m = new Member(
								rset.getInt("USERNO"),
								rset.getString("USERID"),
								rset.getString("USERPW"),
								rset.getString("USERNAME"),
								rset.getString("GENDER") == null ? ' ' : rset.getString("GENDER").charAt(0),
								rset.getInt("AGE"),
								rset.getString("EMAIL"),
								rset.getString("ADDRESS"),
								rset.getString("PHONE"),
								rset.getString("HOBBY"),
								rset.getDate("ENROLLDATE")
								
					);
			//ResultSet 객체에서 각 컬럼의 데이터 뽑아내어 Member 객체를 생성(저장)
					list.add(m);
			
		}
		//반복문이 끝난 시점..
		// 조회된 데이터가 없다면 ? 리스트는 비어 있을것임
		// 조회된 데이터가 있다면 ? 리스트에는 데이터가 한 개 이상 담겨있을 것임
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				rset.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	public Member selectsearchId(String userId) {
		Member m = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		// 실행할 sql문
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'";
		
		System.out.println("---------------------");
		System.out.println(sql);
		System.out.println("---------------------");
		
		try {
		//1) jdbc driver 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		
		stmt = conn.createStatement();
		
		rset = stmt.executeQuery(sql);
		
		if(rset.next()) {
			m = new Member(
						rset.getInt("USERNO"),
						rset.getString("USERID"),
						rset.getString("USERPW"),
						rset.getString("USERNAME"),
						rset.getString("GENDER") == null ? ' ' : rset.getString("GENDER").charAt(0),
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("ADDRESS"),
						rset.getString("PHONE"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE")
						
					);

		}
				
		
		
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public int deleteMember(String userId) {
		
		//jdbc용 객체 선언
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		// 실행할 sql문(쿼리문)
		String sql = "DELETE FROM MEMBER WHERE USERID = '" + userId + "'";
		
		System.out.println("---------------------");
		System.out.println(sql);
		System.out.println("---------------------");
		
		try {
		//1) jdbc driver 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		// Connection 객체 생성
		conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		
		conn.setAutoCommit(false);
		
		//Statement갹채 생성 => Connection 객체로 생성(쿼리문 실행시키는 애)
		stmt = conn.createStatement();
		
		//쿼리문 실행 및 결과 받기
		result = stmt.executeUpdate(sql);
		
		// dml문 실행 => 트랜잭션 처리
		if(result > 0) {
			conn.commit();
		}else {
			conn.rollback();
		}
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int updateMenu(Member m) {
		int result = 0;
		
		
		String sql = "UPDATE MEMBER "
					+ "SET USERPW = ?,"
					+ "USERNAME = ?, "
					+ "ADDRESS = ?, "
					+ "PHONE = ?, "
					+ "HOBBY = ?"
					+ "WHERE USERID = ?";
		
		//JDBC객체 선언 및 null 초기화
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		conn.setAutoCommit(false);
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, m.getUserPw());
		pstmt.setString(2, m.getUserName());
		pstmt.setString(3, m.getAddress());
		pstmt.setString(4, m.getPhone());
		pstmt.setString(5, m.getHobby());
		pstmt.setString(6, m.getUserId());
		
		result = pstmt.executeUpdate();
		
		if(result > 0) {
			conn.commit();
		}else {
			conn.rollback();
		}
		
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		return result;
	}

	public ArrayList<Member> selectUserName(String keyword) {
		ArrayList<Member> list = new ArrayList<>();

		// JDBC용 객체
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		// 실행할 쿼리문
		String sql = "SELECT * FROM MEMBER" + " WHERE USERNAME LIKE ?";


		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%" + keyword + "%");

			rset = pstmt.executeQuery();

			while (rset.next()) {
				Member m = new Member(rset.getInt("USERNO"),
						rset.getString("USERID"), 
						rset.getString("USERPW"),
						rset.getString("USERNAME"),
						rset.getString("GENDER") == null ? ' ' : 
							rset.getString("GENDER").charAt(0), 
						rset.getInt("AGE"),
						rset.getString("EMAIL"), 
						rset.getString("ADDRESS"), 
						rset.getString("PHONE"),
						rset.getString("HOBBY"), 
						rset.getDate("ENROLLDATE")

				);

				list.add(m);

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

}
