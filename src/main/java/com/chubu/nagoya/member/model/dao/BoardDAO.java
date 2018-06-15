package com.chubu.nagoya.member.model.dao;

import java.util.Date;
import java.util.List;

import com.chubu.nagoya.member.model.dto.BoardVO;

public interface BoardDAO {
	
	public List<BoardVO> listAll(Date date) throws Exception;
	
	public void create(BoardVO vo) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public BoardVO read(int brdNo) throws Exception;
	
	public void delete(int brdNo) throws Exception;
}
