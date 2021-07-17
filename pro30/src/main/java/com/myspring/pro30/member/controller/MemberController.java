package com.myspring.pro30.member.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;

@Controller("memberController")
@RequestMapping("/member/*")
public class MemberController {
	@Autowired
	private MemberService memberService;
	private MemberVO memberVO;
	
	@RequestMapping(value = "/listMembers.do", method = RequestMethod.GET)
	public ModelAndView selectAllMembers(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		List<MemberVO> listMembers = memberService.selectAllMembers();
		mav.addObject("listMembers", listMembers);
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value = "/deleteMember.do", method = RequestMethod.GET)
	public ModelAndView deleteMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		memberService.deleteMember(id);
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
	
	@RequestMapping(value = "/*Form.do", method = RequestMethod.GET)
	public ModelAndView form(
							@RequestParam(value = "isLogOn", required = false)String isLogOn,
							@RequestParam(value = "action", required = false) String action,
							HttpServletRequest request,
							HttpServletResponse response
							) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("isLogOn", isLogOn);
		mav.setViewName(viewName);
		return mav;
	}
	
	@RequestMapping(value = "/insertMember.do", method = RequestMethod.POST)
	public ModelAndView insertMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		memberService.insertMember(member);
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
	
	@RequestMapping(value = "/checkId", method = RequestMethod.POST)
	public void checkId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		String id = (String) request.getParameter("id");
		int chkCnt = memberService.checkId(id);
		if(chkCnt == 0) {
			writer.print("PASS");
		}else {
			writer.print("HOLD");
		}
	}
	
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(member);
		if(memberVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", memberVO);
			session.setAttribute("isLogOn", true);
			String action = (String) session.getAttribute("action");
			session.removeAttribute("action");
			if(action != null) {
				mav.setViewName("redirect:/"+action);
			}else {
				mav.setViewName("redirect:/member/listMembers.do");
			}
		}else {
			rAttr.addAttribute("isLogOn", false);
			mav.setViewName("redirect:/member/loginForm.do");
		}
		return mav;
	}
	
	@RequestMapping(value = "/member/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
	
}
