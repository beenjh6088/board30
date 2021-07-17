package com.myspring.pro30.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{

	private final String NAMESPACE = "mapper.member.";
	@Autowired
	private SqlSession sqlSession;
	
	public List<MemberVO> selectAllMembers() throws Exception{
		return sqlSession.selectList(NAMESPACE+"selectAllMembers");
	}
	
	public int deleteMember(String id) throws Exception{
		return sqlSession.delete(NAMESPACE+"deleteMember", id);
	}
	
	public int insertMember(MemberVO memberVO) throws Exception{
		return sqlSession.insert(NAMESPACE+"insertMember", memberVO);
	}
	
	public int checkId(String id) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"checkId", id);
	}
	
	public MemberVO login(MemberVO memberVO) throws Exception{
		return sqlSession.selectOne(NAMESPACE+"login", memberVO);
	}
}
