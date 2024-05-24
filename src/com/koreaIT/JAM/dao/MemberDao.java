package com.koreaIT.JAM.dao;

import java.sql.Connection;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class MemberDao {
	
	private Connection conn;
	
	public MemberDao(Connection connection) {
		this.conn = connection;
	}

	public boolean isLoginIdDupChk(String loginId) {
		
		SecSql sql = new SecSql();
    	sql.append("SELECT COUNT(id) > 0");
    	sql.append("FROM `member`");
    	sql.append("WHERE loginId = ?;", loginId);
    	
    	return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public void joinMember(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();
    	sql.append("INSERT INTO `member`");
    	sql.append("SET regDate = NOW()");
    	sql.append(", updateDate = NOW()");
    	sql.append(", loginId = ?", loginId);
    	sql.append(", loginPw = ?", loginPw);
    	sql.append(", `name` = ?", name);
    	
    	DBUtil.insert(conn, sql);
	}

}
