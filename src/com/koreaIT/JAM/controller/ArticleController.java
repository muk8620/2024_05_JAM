package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;
import com.koreaIT.JAM.session.Session;
import com.koreaIT.JAM.util.Util;

public class ArticleController {

	private ArticleService articleService;
	private Scanner sc;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.articleService = new ArticleService(connection);
		this.sc = sc;
	}
	
	public void doWrite() {
		
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		System.out.println("== 게시물 작성 ==");
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();
    	
    	int id = articleService.doWrite(Session.getLoginedMemberId(), title, body);
        
		System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
	}
	
	public void showList() {
		List<Article> foundArticles = articleService.showList();
		
		if (foundArticles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		
		System.out.println("== 게시물 목록 ==");
		System.out.println("번호	|		제목		|	작성자	|		작성일		|	조회수");
		
		for (Article article : foundArticles) {
			System.out.printf("%d	|		%s		|	%s	|	%s	|	%s\n", article.id, article.title, article.writerName, Util.datetimeFormat(article.regDate), article.vCnt);
		}
	}

	public void showDetail(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다.");
			return;
		}
		
		int affectedRow = articleService.increaseVCnt(id);
		
    	if (affectedRow == 0) {
    		System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
    		return;
    	}
    	
    	Article article = articleService.showDetail(id);
    	
		System.out.println("== 게시물 상세보기 ==");
    	System.out.println("번호 :" + article.id);
    	System.out.println("작성자 :" + article.writerName);
    	System.out.println("작성일 :" + Util.datetimeFormat(article.regDate));
    	System.out.println("작성일 :" + Util.datetimeFormat(article.updateDate));
    	System.out.println("제목 :" + article.title);
    	System.out.println("내용 :" + article.body);
    	System.out.println("조회수 :" + article.vCnt);
		
	}

	public void doModify(String cmd) {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다.");
			return;
		}
		
		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		if (article.memberId != Session.getLoginedMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
    	
		System.out.println("== 게시물 수정 ==");
		
		System.out.printf("수정 할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정 할 내용 : ");
		String body = sc.nextLine().trim();
		
		articleService.doModify(id, title, body);
		
		System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
		
	}

	public void doDelete(String cmd) {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다.");
			return;
		}
		
		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		if (article.memberId != Session.getLoginedMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
    	System.out.println("== 게시물 삭제 ==");
    	
    	articleService.doDelete(id);
    	
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
		
	}
	
	
}
