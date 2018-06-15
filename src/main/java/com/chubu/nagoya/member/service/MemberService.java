package com.chubu.nagoya.member.service;

import java.util.List;

import com.chubu.nagoya.member.model.dto.MemberVO;

public interface MemberService {
	
	public List<MemberVO> memberList();
	
	public void insertMember(MemberVO vo);
	
	public MemberVO viewMember(String userId);
	
	public void deleteMember(String userId);
	
	public void updateMember(MemberVO vo);
	
	public boolean checkPw(String userId, String userPw);

}
