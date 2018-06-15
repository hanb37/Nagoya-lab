package com.chubu.nagoya.member.model.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.chubu.nagoya.member.model.dto.MessageInfo;

@Repository
public class MessageDAOImpl implements MessageDAO{
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public List<MessageInfo> listMessages(Date date) throws Exception {
		return sqlSession.selectList("message.listAll",date);
	}
}
