package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

//Controller : View를 통해서 사용자가 요청한 기능에 대해 처리하는 역할
//				요청받은 메소드에서 전달된 데이터를 가공처리한 후 DAO로 전달하여 호출
//				DAO로부터 반환받은 결과에 따라 성공인지 실패인지를 판단 후 응답

public class MemberController {
	
	/**
	 * 회원 추가 요청을 처리할 메소드
	 * @param userId 회원 아이디
	 * @param userPw 회원 비밀번호
	 * @param name 회원 이름
	 * @param gender 회원 성별
	 */
	public void insertMember(String userId, String userPw, String name, char gender) {
		//view로부터 전달받은 값들을 dao에게 바로 전달하지 않고
		// 어딘가(Member)에 담아서 전달
		
		// 기본생성자 생성 후 setter 메소드 하나하나 저장
		// 매개변수 생성자를 사용하여 객체 생성
		Member m = new Member(userId, userPw, name, gender);
		
		int result = new MemberDao().insertMember(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("회원 추가 성공!");
		}else {
			new MemberMenu().displayFailed("회원 추가 실패!");
		}
		
	}
	/**
	 * 전체 회원 목록 조회 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList();
		
		//조회된 결과에 따라 사용자에게 결과 표시
		if(list.isEmpty()) {
			//조회결과가 없다면
			new MemberMenu().displayNoData("전체 조회 결과가 없음!!");
		}else {
			new MemberMenu().displayMemberList(list);
		}
	}
	public void searchId(String userId) {
		Member m = new MemberDao().selectsearchId(userId);
		
		if(m == null) {
			new MemberMenu().displayNoData(userId+"에 해당하는 결과가 없음!");
		}else {
			new MemberMenu().displayMember(m);
		}
		
	}
	public void deleteMember(String userId) {
		int result = new MemberDao().deleteMember(userId);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("삭제 성공!");
		}else {
			new MemberMenu().displayNoData(userId+"에 해당하는 결과가 없음!");
		
		}
	}

	public void updateMenu(String userId, String userPw, String userName, String address, String phone, String hobby) {
		//전달받은 member객체로 생성하여 DAO에게 전달
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPw(userPw);
		m.setUserName(userName);
		m.setAddress(address);
		m.setPhone(phone);
		m.setHobby(hobby);
		
		// DML(update) -->int 처리된 행 수
		int result = new MemberDao().updateMenu(m);
		
		if(result > 0) {
			new MemberMenu().displaySuccess("회원정보 수정 성공!");
		}else {
			new MemberMenu().displayFailed("실패!");
		}
	}

}
