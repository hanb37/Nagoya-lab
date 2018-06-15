package com.chubu.nagoya.member.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.chubu.nagoya.member.model.dao.BoardDAO;
import com.chubu.nagoya.member.model.dao.MessageDAO;
import com.chubu.nagoya.member.model.dto.BoardVO;
import com.chubu.nagoya.member.model.dto.MessageInfo;

@Service
public class BoardServiceImpl implements BoardService{

	@Inject
	BoardDAO boardDao;
	
	@Inject
	MessageDAO messageDao;
	
	@Override
	public List<BoardVO> listAll(Date date) throws Exception {
		return boardDao.listAll(date);
	}
	
	@Override
	public  List<MessageInfo> listMessages(Date date) throws Exception {
		
		return messageDao.listMessages(date);
	}
	
    @Override
    public void create(BoardVO vo) throws Exception {
        String brdWriter = vo.getBrdWriter();
        String brdComment = vo.getBrdComment();

        brdWriter = brdWriter.replace("<", "&lt;");
        brdWriter = brdWriter.replace("<", "&gt;");

        brdComment = brdComment.replace("\n", "<br>");
        vo.setBrdWriter(brdWriter);
        vo.setBrdComment(brdComment);
        boardDao.create(vo);
    }

	@Override
	public void update(BoardVO vo) throws Exception {
		boardDao.update(vo);
	}

	@Override
	public BoardVO read(int brdNo) throws Exception {
		return boardDao.read(brdNo);
	}

	@Override
	public void delete(int brdNo) throws Exception {
		boardDao.delete(brdNo);
	}


}
