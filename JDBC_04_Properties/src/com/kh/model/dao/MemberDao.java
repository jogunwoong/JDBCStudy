package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.model.vo.Member;

public class MemberDao {
	
	Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public int insertMember(Connection conn, Member m) {


		PreparedStatement pstmt = null;
		int result = 0;
		

		try {
			String sql = prop.getProperty("insertMember");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPw());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getAddress());
			pstmt.setString(8, m.getPhone());
			pstmt.setString(9, m.getHobby());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<Member> selectList(Connection conn) {
		ArrayList<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;


		try {
			
			String sql = prop.getProperty("selectMemberList");
			
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Member m = new Member(
						rset.getInt("USERNO"),
						rset.getString("USERID"), 
						rset.getString("USERPW"),
						rset.getString("USERNAME"), 
						rset.getString("GENDER"), 
						rset.getInt("AGE"),
						rset.getString("EMAIL"), 
						rset.getString("ADDRESS"), 
						rset.getString("PHONE"),
						rset.getString("HOBBY"), 
						rset.getDate("ENROLLDATE"));
				list.add(m);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}finally{
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}

	public int updateMenu(Connection conn, Member m) {
		
		String sql = "UPDATE MEMBER "
				+ "SET USERPW = ?,"
				+ "USERNAME = ?, "
				+ "ADDRESS = ?, "
				+ "PHONE = ?, "
				+ "HOBBY = ?"
				+ "WHERE USERID = ?";
		
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserPw());
			pstmt.setString(2, m.getUserName());
			pstmt.setString(3, m.getAddress());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getHobby());
			pstmt.setString(6, m.getUserId());
			
			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			JDBCTemplate.close(pstmt);
		}
		return result;
		
	}

	public int deleteMember(Connection conn, String userId) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";

		try {
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userId);
		
		result = pstmt.executeUpdate();
		
		}catch(SQLException e) {
			e.printStackTrace();

				JDBCTemplate.close(pstmt);
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	

}