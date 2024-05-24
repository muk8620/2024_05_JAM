package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dao.ArticleDao;
import com.koreaIT.JAM.dto.Article;

public class ArticleService {
	
	private ArticleDao articleDao;
	
	public ArticleService(Connection connection) {
		articleDao = new ArticleDao(connection);
	}

	public int writeArticle(String title, String body) {
		return articleDao.writeArticle(title, body);
	}

	public List<Article> showArticleList() {
		return articleDao.showArticleList();
	}

	public Map<String, Object> getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public int isArticleExist(int id) {
		return articleDao.isArticleExist(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleDao.modifyArticle(id, title, body);
	}

	public void deleteArticle(int id) {
		articleDao.deleteArticle(id);
	}

}
