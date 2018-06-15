package com.chubu.nagoya.member.service;

import java.util.Date;
import java.util.List;

import com.chubu.nagoya.member.model.dto.BoardVO;
import com.chubu.nagoya.member.model.dto.MessageInfo;

public interface BoardService {
	public List<BoardVO> listAll(Date date) throws Exception;
	
	public void create(BoardVO vo) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public BoardVO read(int brdNo) throws Exception;
	
	public void delete(int brdNo) throws Exception;
	
	public List<MessageInfo> listMessages(Date date) throws Exception;
}
