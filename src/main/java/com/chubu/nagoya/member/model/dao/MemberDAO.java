package com.chubu.nagoya.member.model.dao;

import java.util.List;

import com.chubu.nagoya.member.model.dto.MemberVO;

public interface MemberDAO {
	public List<MemberVO> memberList() throws Exception;
	
	public void insertMember(MemberVO vo);
	
	public MemberVO viewMember(String userId);
	
	public void deleteMember(String userId);
	
	public void updateMember(MemberVO vo);
	
	public boolean checkPw(String userId, String userPw);

}
