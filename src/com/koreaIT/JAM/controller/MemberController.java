package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.service.MemberService;

public class MemberController {
	
	public MemberService memberService;
	public Scanner sc;
	
	public MemberController(Connection connection, Scanner sc) {
		this.memberService = new MemberService(connection);
		this.sc = sc;
	}

	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String name = null;
		System.out.println("== 회원가입 ==");
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.isEmpty()) {
				System.out.println("아이디는 필수 입력 정보입니다");
				continue;
			}
			
        	boolean isLoginIdDup = memberService.isLoginIdDupChk(loginId);
        	
        	if (isLoginIdDup) {
        		System.out.printf("%s은(는) 이미 존재하는 아이디 입니다.\n", loginId);
        		continue;
        	}
        	System.out.printf("[ %s ] 은(는) 사용가능한 아이디입니다\n", loginId);
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.isEmpty()) {
				System.out.println("비밀번호는 필수 입력 정보입니다");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine().trim();
			
			if (!loginPw.equals(loginPwChk)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.isEmpty()) {
				System.out.println("이름은 필수 입력 정보입니다");
				continue;
			}
			break;
		}
    	
    	memberService.doJoin(loginId, loginPw, name);
    	
    	System.out.printf("%s회원님의 가입을 환영합니다.\n", name);
	}
}
