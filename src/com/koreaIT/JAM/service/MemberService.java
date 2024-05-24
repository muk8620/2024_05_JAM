package com.koreaIT.JAM.service;

import java.sql.Connection;

import com.koreaIT.JAM.dao.MemberDao;

public class MemberService {
	
	public MemberDao memberDao;
	
	public MemberService(Connection connection) {
		memberDao = new MemberDao(connection);
	}

	public boolean isLoginIdDupChk(String loginId) {
		return memberDao.isLoginIdDupChk(loginId);
	}

	public void joinMember(String loginId, String loginPw, String name) {
		memberDao.joinMember(loginId, loginPw, name);
	}

}
