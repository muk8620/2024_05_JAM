package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class App {
	
	private static final String URL = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    private static final String USER = "root";
    private static final String PASSWORD = "";
	
	public void run() {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("article write")) {
					System.out.println("== 게시물 작성 ==");
					
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String body = sc.nextLine();
					
					SecSql sql = new SecSql();
		        	sql.append("INSERT INTO article");
		        	sql.append("SET regDate = NOW()");
		        	sql.append(", updateDate = NOW()");
		        	sql.append(", title = ?", title);
		        	sql.append(", `body` = ?", body);
		        	
		        	int id = DBUtil.insert(connection, sql);
		            
					System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
					
				} else if (cmd.equals("article list")) {
					List<Article> articles = new ArrayList<>();
					
					SecSql sql = new SecSql();
		        	sql.append("SELECT * FROM article");
		        	sql.append("ORDER BY id DESC;");
		        	
		        	List<Map<String, Object>> articleListMap = DBUtil.selectRows(connection, sql);
		        	
		        	for (Map<String, Object> articleMap : articleListMap) {
		        		articles.add(new Article(articleMap));
		        	}
					
					if (articles.size() == 0) {
						System.out.println("게시물이 존재하지 않습니다.");
						continue;
					}
					
					System.out.println("== 게시물 목록 ==");
					System.out.println("번호	|		제목		|		작성일		");
					
					for (Article article : articles) {
						System.out.printf("%d	|		%s		|	%s\n", article.id, article.title, article.regDate);
					}
					
				} else if (cmd.startsWith("article detail ")) {
					int id = 0;
					
					try {
						id = Integer.parseInt(cmd.split(" ")[2]); 
					} catch (NumberFormatException e) {
						System.out.println("명령어가 올바르지 않습니다.");
						continue;
					} 
					
					SecSql sql = new SecSql();
					sql.append("SELECT * FROM article");
		        	sql.append("WHERE id = ?;", id);
		        	
		        	Map<String, Object> articleMap = DBUtil.selectRow(connection, sql);
		        	
		        	if (articleMap.isEmpty()) {
		        		System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
		        		continue;
		        	}
		        	
		        	Article article = new Article(articleMap);
		        	
					System.out.println("== 게시물 상세보기 ==");
		        	System.out.println("번호 :" + article.id);
		        	System.out.println("작성일 :" + article.regDate);
		        	System.out.println("수정일 :" + article.updateDate);
		        	System.out.println("제목 :" + article.title);
		        	System.out.println("내용 :" + article.body);
					
				} else if (cmd.startsWith("article modify ")) {
					int id = 0;
					
					try {
						id = Integer.parseInt(cmd.split(" ")[2]); 
					} catch (NumberFormatException e) {
						System.out.println("명령어가 올바르지 않습니다.");
						continue;
					} 
					
					SecSql sql = new SecSql();
		        	sql.append("SELECT COUNT(id) FROM article");
		        	sql.append("WHERE id = ?;", id);
		        	
		        	int articleCount = DBUtil.selectRowIntValue(connection, sql);
		        	
		        	if (articleCount == 0) {
		        		System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
		        		continue;
		        	}
					
					System.out.println("== 게시물 수정 ==");
					
					System.out.printf("수정 할 제목 : ");
					String title = sc.nextLine();
					System.out.printf("수정 할 내용 : ");
					String body = sc.nextLine();
		            
					sql = new SecSql();
		        	sql.append("UPDATE article");
		        	sql.append("SET updateDate = NOW()");
		        	sql.append(", title = ?", title);
		        	sql.append(", `body` = ?", body);
		        	sql.append("WHERE id = ?", id);
					
		        	DBUtil.update(connection, sql);
					
					System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
					
				} else if (cmd.startsWith("article delete ")) {
					int id = 0;
					
					try {
						id = Integer.parseInt(cmd.split(" ")[2]); 
					} catch (NumberFormatException e) {
						System.out.println("명령어가 올바르지 않습니다.");
						continue;
					} 
					
					SecSql sql = new SecSql();
		        	sql.append("SELECT id FROM article");
		        	sql.append("WHERE id = ?;", id);
		        	
		        	int articleCount = DBUtil.selectRowIntValue(connection, sql);
		        	
		        	if (articleCount == 0) {
		        		System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
		        		continue;
		        	}
					
					System.out.println("== 게시물 삭제 ==");
					
					sql = new SecSql();
		        	sql.append("DELETE from article");
		        	sql.append("WHERE id = ?", id);
					
		        	DBUtil.delete(connection, sql);
					
					System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
					
				} else {
					System.out.println("존재하지 않는 명령어 입니다.");
					continue;
				}
				
				if (cmd.equals("exit")) {
					break;
				}
			}
			
        } catch (SQLException e) {
        	System.out.println("에러 : " + e);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (connection != null) {
            	try {
            		connection.close();
            	} catch (SQLException e) {
            		e.printStackTrace();
            	}
            }
        }
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}
}
