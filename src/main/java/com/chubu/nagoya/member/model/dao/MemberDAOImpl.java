package com.chubu.nagoya.member.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.chubu.nagoya.member.model.dto.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public List<MemberVO> memberList() {
		return sqlSession.selectList("member.memberList");
	}
	@Override
	public void insertMember(MemberVO vo) {
		sqlSession.insert("member.insertMember", vo);
	}
	@Override
	public MemberVO viewMember(String userId) {
		return sqlSession.selectOne("member.viewMember", userId);
	}
	@Override
	public void deleteMember(String userId) {
		sqlSession.delete("member.deleteMember", userId);
	}
	@Override
	public void updateMember(MemberVO vo) {
		sqlSession.update("member.updateMember", vo);
	}
	
	// 06. 회원 정보 수정 및 삭제를 위한 비밀번호 체크
    @Override
    public boolean checkPw(String userId, String userPw) {
        boolean result = false;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("userPw", userPw);
        int count = sqlSession.selectOne("member.checkPw", map);
        if(count == 1) result= true;
        return result;
    }
}
