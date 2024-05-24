package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;

public class ArticleController {

	private ArticleService articleService;
	private Scanner sc;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.articleService = new ArticleService(connection);
		this.sc = sc;
	}
	
	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();
    	
    	int id = articleService.writeArticle(title, body);
        
		System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
	}
	
	public void showList() {
		List<Article> foundArticles = articleService.showArticleList();
		
		if (foundArticles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		
		System.out.println("== 게시물 목록 ==");
		System.out.println("번호	|		제목		|		작성일		");
		
		for (Article article : foundArticles) {
			System.out.printf("%d	|		%s		|	%s\n", article.id, article.title, article.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
		}
	}

	public void showDetail(String cmd) {
		
		int id = 0;
		
		try {
			id = Integer.parseInt(cmd.split(" ")[2]); 
		} catch (NumberFormatException e) {
			System.out.println("명령어가 올바르지 않습니다.");
			return;
		} 
		
		Map<String, Object> articleMap = articleService.getArticleById(id);
		
    	if (articleMap.isEmpty()) {
    		System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
    		return;
    	}
    	
    	Article article = new Article(articleMap);
    	
		System.out.println("== 게시물 상세보기 ==");
    	System.out.println("번호 :" + article.id);
    	System.out.println("작성일 :" + article.regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
    	System.out.println("수정일 :" + article.updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
    	System.out.println("제목 :" + article.title);
    	System.out.println("내용 :" + article.body);
		
	}

	public void doModify(String cmd) {
		int id = 0;
		
		try {
			id = Integer.parseInt(cmd.split(" ")[2]); 
		} catch (NumberFormatException e) {
			System.out.println("명령어가 올바르지 않습니다.");
			return;
		} 
		
		int articleCount = articleService.isArticleExist(id);
    	
    	if (articleCount == 0) {
    		System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
    		return;
    	}
		
		System.out.println("== 게시물 수정 ==");
		
		System.out.printf("수정 할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정 할 내용 : ");
		String body = sc.nextLine().trim();
        
		articleService.modifyArticle(id, title, body);
		
		System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
		
	}

	public void doDelete(String cmd) {
		int id = 0;
		
		try {
			id = Integer.parseInt(cmd.split(" ")[2]); 
		} catch (NumberFormatException e) {
			System.out.println("명령어가 올바르지 않습니다.");
			return;
		} 
		
		int articleCount = articleService.isArticleExist(id);
    	
    	if (articleCount == 0) {
    		System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
    		return;
    	}
		
    	articleService.deleteArticle(id);
    	
		System.out.println("== 게시물 삭제 ==");
		
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
		
	}
	
	
}
