package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

//View : 사용자가 보게 될 시각적인 요소 (화면) 출력 및 입력
public class MemberMenu {
	//Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	// MemberController 객체 생성
	private MemberController mc = new MemberController();
	
	/*
	 * 사용자가 보게 될 첫 화면 : 메인메뉴(화면)
	 */
	
	public void mainMenu() {
		while (true) {
			System.out.println("===회원 관리 프로그램===");
			System.out.println("1. 회원추가"); // C(create)
			System.out.println("2. 전체 회원 조회"); // R(read)
			System.out.println("3. 회원 아이디로 검색"); // R(read)
			System.out.println("4. 회원 정보 수정"); // U(update)
			System.out.println("5. 회원 탈퇴"); // D(delete)
			System.out.println("6. 회원 이름으로 검색");
			System.out.println("9. 프로그램 종료");

			System.out.print(">> 메뉴 번호 :");
			int menu = sc.nextInt();
			sc.nextLine();

			switch (menu) {
			case 1:
				addMenu();
				break;
			case 2:
				mc.selectList();
				break;
			case 3:
				searchId();
				break;
			case 4:
				updateMenu();
				break;
			case 5:
				withdraw();
				break;
			case 6:
				searchName();
				break;
			case 9:
				System.out.println("프로그램 종료 ...");
				return;

			}

		}
	}


	public void addMenu() {
		System.out.print("아이디 : ");
		String userId = sc.nextLine();

		System.out.print("비밀번호 : ");
		String userPw = sc.nextLine();

		System.out.print("이름 : ");
		String name = sc.nextLine();

		System.out.print("성별 (M/F)");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		//회원추가 요청 --> Controller에게 요청
		mc.insertMember(userId, userPw, name, gender);
		

	}
	
	/**
	 * 요청 처리 후 성공했을 경우 사용자에게 표시할 화면
	 * @param message 성공 메시지
	 */
	
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 : " + message);
	}
	
	/**
	 * 요청 처리 후 실패 했을 경우 사용자에게 표시할 화면
	 * @param message 실패 메시지
	 */
	public void displayFailed(String message) {
		System.out.println("서비스 요청 실패 : " + message); 
	}
	/**
	 * 조회 결과가 없을 때 사용자에게 표시할 화면
	 * @param message 조회 결과에 메시지
	 */
	public void displayNoData(String message) {
		System.out.println(message);
	}
	/**
	 * 조회 결과가 여러행일 때 사용자에게 표시할 화면
	 * @param list 조회된 회원 정보가 담겨있는 리스트
	 */
	public void displayMemberList(ArrayList<Member> list) {
		System.out.println("---조회결과---");
		
		/*for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		*/
		for(Member m : list) {
			System.out.println(m);
		}
	}
	public void searchId() {
		System.out.print("조회할 아이디");
		String userId = sc.nextLine();
		
		mc.searchId(userId);
	}
	public void displayMember(Member m) {
		System.out.print("---조회결과----");
		System.out.println(m);
		
	}
	public void withdraw() {
		System.out.print("탈퇴할 아이디 : ");
		String userId = sc.nextLine();
		
		mc.deleteMember(userId);
	}
	public void updateMenu() {
		System.out.print("회원 아이디 : ");
		String id = sc.nextLine();
		
		System.out.print("변경할 비밀번호 : ");
		String pwd = sc.nextLine();
		
		System.out.print("변경할 이름 : ");
		String name = sc.nextLine();
		
		System.out.print("변경할 주소 : ");
		String addr = sc.nextLine();
		
		System.out.print("변경할 연락처 : ");
		String phone = sc.nextLine();
		
		System.out.print("변경할 취미");
		String hobby = sc.nextLine();
		
		//컨트롤러에게 입력받은 값 전달 회원정보 수정해줘! 요청
		mc.updateMenu(id, pwd, name, addr, phone, hobby);
	}
	private void searchName() {
		System.out.println("--- 회원 이름으로 키워드 검색---");
		
		System.out.print("검색할 회원명 입력 : ");
		String name = sc.nextLine();
		
		mc.searchName(name);
		
	}
	
}
