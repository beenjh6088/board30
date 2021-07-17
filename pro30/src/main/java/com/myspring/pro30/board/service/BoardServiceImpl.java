package com.myspring.pro30.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myspring.pro30.board.dao.BoardDAO;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	public List<ArticleVO> selectAllArticles() throws Exception{
		return boardDAO.selectAllArticles();
	}
	
	public List<ArticleVO> selectAllArticles(Map<String, Object> paramMap) throws Exception{
		return boardDAO.selectAllArticles(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int insertNewArticle(Map<String, Object> articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
		List<ImageVO> imageFileList = (List<ImageVO>) articleMap.get("imageFileList");
		if(imageFileList != null) {
			boardDAO.insertNewImage(articleMap);
		}
		return articleNO;
	}
	
	public int countTotalArticles() throws Exception{
		return boardDAO.countTotalArticles();
	}
	
	public ArticleVO selectArticle(int articleNO) throws Exception{
		return boardDAO.selectArticle(articleNO);
	}
	
	public int modArticle(Map<String, Object> articleMap) throws Exception{
		return boardDAO.modArticle(articleMap);
	}
	
	public int removeArticle(int articleNO) throws Exception{
		return boardDAO.removeArticle(articleNO);
	}
	
	public List<ImageVO> findImageFile(int articleNO) throws Exception{
		return boardDAO.findImageFile(articleNO);
	}
	
	public int modImage(Map<String, Object> articleMap) throws Exception{
		return boardDAO.modImage(articleMap);
	}
	
	public int insertNewImage(Map<String, Object> articleMap) throws Exception{
		return boardDAO.insertNewImage(articleMap);
	}
	
	public int insertRepArticle(Map<String, Object> articleMap) throws Exception{
		return boardDAO.insertRepArticle(articleMap);
	}
}
