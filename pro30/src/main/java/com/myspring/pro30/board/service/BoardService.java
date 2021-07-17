package com.myspring.pro30.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

public interface BoardService {

	public List<ArticleVO> selectAllArticles() throws Exception;
	
	public List<ArticleVO> selectAllArticles(Map<String, Object> paramMap) throws Exception;
	
	public int insertNewArticle(Map<String, Object> articleMap) throws Exception;
	
	public int countTotalArticles() throws Exception;
	
	public ArticleVO selectArticle(int articleNO) throws Exception;
	
	public int modArticle(Map<String, Object> articleMap) throws Exception;
	
	public int removeArticle(int articleNO) throws Exception;
	
	public List<ImageVO> findImageFile(int articleNO) throws Exception;
	
	public int modImage(Map<String, Object> articleMap) throws Exception;
	
	public int insertNewImage(Map<String, Object> articleMap) throws Exception;
	
	public int insertRepArticle(Map<String, Object> articleMap) throws Exception;
}
