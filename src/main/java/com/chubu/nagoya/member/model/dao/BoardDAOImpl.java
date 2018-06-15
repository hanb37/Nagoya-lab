package com.chubu.nagoya.member.model.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.chubu.nagoya.member.model.dto.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Inject
	SqlSession sqlSession;
	
	@Override
	public List<BoardVO> listAll(Date date) throws Exception {
		return sqlSession.selectList("board.listAll",date);
	}

	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert("board.insert", vo);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update("board.updateArticle", vo);
	}

	@Override
	public BoardVO read(int brdNo) throws Exception {
		return sqlSession.selectOne("board.view", brdNo);
	}

	@Override
	public void delete(int brdNo) throws Exception {
		sqlSession.delete("board.deleteArticle", brdNo);
	}
	
}
