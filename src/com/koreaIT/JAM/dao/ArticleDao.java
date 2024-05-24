package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleDao {
	
	private Connection connection;
	
	public ArticleDao(Connection connection) {
		this.connection = connection;
	}

	public int writeArticle(String title, String body) {
		SecSql sql = new SecSql();
    	sql.append("INSERT INTO article");
    	sql.append("SET regDate = NOW()");
    	sql.append(", updateDate = NOW()");
    	sql.append(", title = ?", title);
    	sql.append(", `body` = ?", body);
    	
    	return DBUtil.insert(connection, sql);
	}

	public List<Article> showArticleList() {
		List<Article> articles = new ArrayList<>();
		SecSql sql = new SecSql();
    	sql.append("SELECT * FROM article");
    	sql.append("ORDER BY id DESC;");
    	
    	List<Map<String, Object>> articleListMap = DBUtil.selectRows(connection, sql);
    	
    	for (Map<String, Object> articleMap : articleListMap) {
    		articles.add(new Article(articleMap));
    	}
    	
    	return articles;
	}

	public Map<String, Object> getArticleById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
    	sql.append("WHERE id = ?;", id);
    	
    	return DBUtil.selectRow(connection, sql);
	}

	public int isArticleExist(int id) {
		
		SecSql sql = new SecSql();
    	sql.append("SELECT COUNT(id) FROM article");
    	sql.append("WHERE id = ?;", id);
    	
    	return DBUtil.selectRowIntValue(connection, sql);
	}

	public void modifyArticle(int id, String title, String body) {
		SecSql sql = new SecSql();
    	sql.append("UPDATE article");
    	sql.append("SET updateDate = NOW()");
    	sql.append(", title = ?", title);
    	sql.append(", `body` = ?", body);
    	sql.append("WHERE id = ?", id);
		
    	DBUtil.update(connection, sql);
	}

	public void deleteArticle(int id) {
		SecSql sql = new SecSql();
    	sql.append("DELETE from article");
    	sql.append("WHERE id = ?", id);
		
    	DBUtil.delete(connection, sql);
	}
	
}
