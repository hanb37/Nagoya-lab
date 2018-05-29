package com.chubu.nagoya.member.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.chubu.nagoya.member.model.dao.BoardDAO;
import com.chubu.nagoya.member.model.dto.BoardVO;

@Service
public class BoardServiceImpl implements BoardService{

	@Inject
	BoardDAO boardDao;
	
	@Override
	public List<BoardVO> listAll() throws Exception {
		return boardDao.listAll();
	}

	// 01. 게시글쓰기
    @Override
    public void create(BoardVO vo) throws Exception {
        String brdWriter = vo.getBrdWriter();
        String brdComment = vo.getBrdComment();
        // *태그문자 처리 (< ==> &lt; > ==> &gt;)
        // replace(A, B) A를 B로 변경
        brdWriter = brdWriter.replace("<", "&lt;");
        brdWriter = brdWriter.replace("<", "&gt;");
        // *공백문자 처리
        brdWriter = brdWriter.replace("  ",    "&nbsp;&nbsp;");
        // *줄바꿈 문자처리
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
