package com.myspring.pro30.member.service;

import java.util.List;

import com.myspring.pro30.member.vo.MemberVO;

public interface MemberService {

	public List<MemberVO> selectAllMembers() throws Exception;
	
	public int deleteMember(String id) throws Exception;
	
	public int insertMember(MemberVO memberVO) throws Exception;
	
	public int checkId(String id) throws Exception;
	
	public MemberVO login(MemberVO memberVO) throws Exception;
}
