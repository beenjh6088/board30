package com.myspring.pro30.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myspring.pro30.member.dao.MemberDAO;
import com.myspring.pro30.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDAO;
	
	public List<MemberVO> selectAllMembers() throws Exception{
		return memberDAO.selectAllMembers();
	}
	
	public int deleteMember(String id) throws Exception{
		return memberDAO.deleteMember(id);
	}
	
	public int insertMember(MemberVO memberVO) throws Exception{
		return memberDAO.insertMember(memberVO);
	}
	
	public int checkId(String id) throws Exception{
		return memberDAO.checkId(id);
	}
	
	public MemberVO login(MemberVO memberVO) throws Exception{
		return memberDAO.login(memberVO);
	}
}
