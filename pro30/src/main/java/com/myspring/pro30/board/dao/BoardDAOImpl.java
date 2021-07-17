package com.myspring.pro30.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	private final String NAMESPACE = "mapper.board.";
	@Autowired
	private SqlSession sqlSession;
	
	public List<ArticleVO> selectAllArticles() throws Exception{
		return sqlSession.selectList(NAMESPACE+"selectAllArticles");
	}
	
	public List<ArticleVO> selectAllArticles(Map<String, Object> paramMap) throws Exception{
		return sqlSession.selectList(NAMESPACE+"selectAllArticles", paramMap);
	}
	
	public int insertNewArticle(Map<String, Object> articleMap) throws Exception{
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		sqlSession.insert(NAMESPACE+"insertNewArticle", articleMap);
		return articleNO;
	}
	
	private int selectNewArticleNO() throws Exception{
		return sqlSession.selectOne(NAMESPACE+"selectNewArticleNO");
	}
	
	public int countTotalArticles() throws Exception{
		return sqlSession.selectOne(NAMESPACE+"countTotalArticles");
	}
	
	public ArticleVO selectArticle(int articleNO) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"selectArticle", articleNO);
	}
	
	public int modArticle(Map<String, Object> articleMap) throws Exception{
		return sqlSession.update(NAMESPACE+"updateArticle", articleMap);
	}
	
	public int removeArticle(int articleNO) throws Exception{
		return sqlSession.delete(NAMESPACE+"deleteArticle", articleNO);
	}
	
	@SuppressWarnings("unchecked")
	public int insertNewImage(Map<String, Object> articleMap) throws Exception{
		List<ImageVO> imageFileList = (ArrayList<ImageVO>)articleMap.get("imageFileList");
		int articleNO = (Integer)articleMap.get("articleNO");
		int imageFileNO = selectNewImageFileNO();
		for(ImageVO imageVO : imageFileList) {
			imageVO.setImageFileNO(++imageFileNO);
			imageVO.setArticleNO(articleNO);
		}
		return sqlSession.insert(NAMESPACE+"insertNewImage", imageFileList);
	}
	
	private int selectNewImageFileNO() throws Exception{
		return sqlSession.selectOne(NAMESPACE+"selectNewImageFileNO");
	}
	
	public List<ImageVO> findImageFile(int articleNO) throws Exception{
		return sqlSession.selectList(NAMESPACE+"findImageFile", articleNO);
	}
	
	@SuppressWarnings("unchecked")
	public int modImage(Map<String, Object> articleMap) throws Exception{
		int imgUpdateCount = 0;
		List<String> imageFileNames = (ArrayList<String>)articleMap.get("imageFileNames");
		List<Integer> imageFileNOs = (ArrayList<Integer>)articleMap.get("imageFileNOs");
		for(int i = 0; i < imageFileNames.size(); i++) {
			String imageFileName = imageFileNames.get(i);
			int imageFileNO = imageFileNOs.get(i);
			articleMap.put("imageFileNO", imageFileNO);
			articleMap.put("imageFileName", imageFileName);
			imgUpdateCount += sqlSession.update(NAMESPACE+"updateImage", articleMap);
		}
		return imgUpdateCount;
	}
	
	public int insertRepArticle(Map<String, Object> articleMap) throws Exception{
		int articleNO = selectNewArticleNO();
		articleMap.put("articleNO", articleNO);
		return sqlSession.insert(NAMESPACE+"insertRepArticle", articleMap);
	}
}
