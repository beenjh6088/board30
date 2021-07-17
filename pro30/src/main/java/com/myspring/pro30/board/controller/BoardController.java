package com.myspring.pro30.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import com.myspring.pro30.member.vo.MemberVO;

@Controller("boardController")
public class BoardController {
	private static final String ARTICLE_IMAGE_REPO = "C:\\DEV\\spring\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	
	@RequestMapping(value = "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		String _section = request.getParameter("section");
		String _pageNum = request.getParameter("pageNum");
		int section = Integer.parseInt((_section == null) ? "1" : _section);
		int pageNum = Integer.parseInt((_pageNum == null) ? "1" : _pageNum);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("section", section);
		paramMap.put("pageNum", pageNum);
		List<ArticleVO> articlesList = boardService.selectAllArticles(paramMap);
		int totArticles = boardService.countTotalArticles();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("section", section);
		resultMap.put("pageNum", pageNum);
		resultMap.put("articlesList", articlesList);
		resultMap.put("totArticles", totArticles);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("resultMap", resultMap);
		return mav;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		String imageFileName=null;
		
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		
		List<String> fileList = upload(multipartRequest);
		List<ImageVO> imageFileList = new ArrayList<ImageVO>();
		if(fileList != null && fileList.size() != 0) {
			for(String fileName : fileList) {
				ImageVO imageVO = new ImageVO();
				imageVO.setImageFileName(fileName);
				imageFileList.add(imageVO);
			}
			articleMap.put("imageFileList", imageFileList);
		}
		
		String message = null;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int articleNO = boardService.insertNewArticle(articleMap);
			if(imageFileList != null && imageFileList.size() != 0) {
				for(ImageVO imageVO : imageFileList) {
					imageFileName = imageVO.getImageFileName();
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\temp\\"+imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
			message =  "<script>";
			message += "	alert('새글을 추가했습니다.');";
			message += "	location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do';";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageVO  imageVO : imageFileList) {
					imageFileName = imageVO.getImageFileName();
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
					srcFile.delete();
				}
			}
			message =  "<script>";
			message += "	alert('오류가 발생했습니다. 다시 시도해주세요.');";
			message += "	location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do';";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	@RequestMapping(value = "/board/*Form.do", method = {RequestMethod.GET})
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		if(viewName.indexOf("replyForm") > -1) {
			mav.addObject("parentNO", request.getParameter("parentNO"));
		}
		mav.setViewName(viewName);
		return mav;
	}
	
	private List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList = new ArrayList<String>();
		List<Integer> imageFileNOs = new ArrayList<Integer>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName = mFile.getOriginalFilename();
			if(!originalFileName.equals("")) {
				int imageFileNO = Integer.parseInt(fileName.replaceAll("[^0-9]+", ""))+1;
				imageFileNOs.add(imageFileNO);
				fileList.add(originalFileName);
			}
			File file = new File(ARTICLE_IMAGE_REPO+"\\"+fileName);
			if(mFile.getSize() != 0) {
				if(! file.exists()) {
					file.createNewFile();
				}
				File tempFile = new File(ARTICLE_IMAGE_REPO+"\\temp\\"+originalFileName);
				mFile.transferTo(tempFile);
				file.delete();
			}
		}
		multipartRequest.setAttribute("imageFileNOs", imageFileNOs);
		return fileList;
	}
	
	@RequestMapping(value = "/board/viewArticle.do", method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		articleVO = boardService.selectArticle(articleNO);
		String viewName = (String) request.getAttribute("viewName");
		List<ImageVO> arrImageFile = boardService.findImageFile(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("article", articleVO);
		mav.addObject("arrImageFile", arrImageFile);
		mav.addObject("arrImageFileSize", arrImageFile.size());
		return mav;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/board/modArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration<Object> enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		List<String> imageFileNames = upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("id", id);
		articleMap.put("imageFileNames", imageFileNames);
		articleMap.put("imageFileNOs", multipartRequest.getAttribute("imageFileNOs"));
		
		String articleNO = (String) articleMap.get("articleNO");
		String message;
		ResponseEntity<String> resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			boardService.modArticle(articleMap);
			if(imageFileNames != null && imageFileNames.size() != 0) {
				for(String imageFileName : imageFileNames) {
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\temp\\"+imageFileName);
					File desDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					File desFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+imageFileName);
					if(!desFile.exists()) {
						FileUtils.moveFileToDirectory(srcFile, desDir, true);
					}
					File p_srcFile = srcFile.getParentFile();
					while(true) {
						File[] srcFileList = p_srcFile.listFiles();
						for(int j = 0; j < srcFileList.length; j++) {
							srcFileList[j].delete();
							System.out.println("파일 삭제");
						}
						break;
					}
				}
				boardService.modImage(articleMap);
			}
			message =  "<script>";
			message += "	alert('글을 수정했습니다.');";
			message += "	location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
			message += "</script>";
			resEnt = new ResponseEntity<String>(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			for(String imageFileName : imageFileNames) {
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				srcFile.delete();
			}
			message =  "<script>";
			message += "	alert('오류가 발생했습니다.다시 수정해주세요');";
			message += "	location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
			message += "</script>";
			resEnt = new ResponseEntity<String>(message, responseHeaders, HttpStatus.CREATED);
		}
		return resEnt;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/board/removeArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity removeArticle(@RequestParam("articleNO") int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			boardService.removeArticle(articleNO);
			File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
			FileUtils.deleteDirectory(destDir);
			
			message =  "<script>";
			message += "	alert('글을 삭제했습니다.');";
			message += "	location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message += "</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			message =  "<script>";
			message += "	alert('작업중 오류가 발생했습니다.다시 시도해 주세요.');";
			message += "	location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message += "</script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/board/addReply.do", method = RequestMethod.POST)
	public ModelAndView addReply(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration<Object> enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		List<String> imageFileNames = upload(multipartRequest);
		List<ImageVO> imageFileList = new ArrayList<ImageVO>();
		if(imageFileNames != null && imageFileNames.size() != 0) {
			for(String fileName : imageFileNames) {
				ImageVO imageVO = new ImageVO();
				imageVO.setImageFileName(fileName);
				imageFileList.add(imageVO);
			}
			articleMap.put("imageFileList", imageFileList);
		}
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("id", id);
		
		String articleNO = (String) articleMap.get("articleNO");
		try {
			boardService.insertRepArticle(articleMap);
			if(imageFileNames != null && imageFileNames.size() != 0) {
				for(String imageFileName : imageFileNames) {
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\temp\\"+imageFileName);
					File desDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					File desFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+imageFileName);
					if(!desFile.exists()) {
						FileUtils.moveFileToDirectory(srcFile, desDir, true);
					}
					File p_srcFile = srcFile.getParentFile();
					while(true) {
						File[] srcFileList = p_srcFile.listFiles();
						for(int j = 0; j < srcFileList.length; j++) {
							srcFileList[j].delete();
							System.out.println("파일 삭제");
						}
						break;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/board/listArticles.do");
		return mav;
	}
}
