package com.chubu.nagoya.member.service;

import java.util.List;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.chubu.nagoya.member.model.dao.MemberDAOImpl;
import com.chubu.nagoya.member.model.dto.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	MemberDAOImpl memberDao;

	@Override
	public List<MemberVO> memberList() {
		return memberDao.memberList();
	}
	@Override
	public void insertMember(MemberVO vo) {
		memberDao.insertMember(vo);
	}
	@Override
	public MemberVO viewMember(String userId) {
		return memberDao.viewMember(userId);
	}
	@Override
	public void deleteMember(String userId) {
		memberDao.deleteMember(userId);
	}
	@Override
	public void updateMember(MemberVO vo) {
		memberDao.updateMember(vo);
	}
    @Override
    public boolean checkPw(String userId, String userPw) {
        return memberDao.checkPw(userId, userPw);
    }
}
