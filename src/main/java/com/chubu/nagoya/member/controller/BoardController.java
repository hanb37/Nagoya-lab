package com.chubu.nagoya.member.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chubu.nagoya.member.model.dto.BoardVO;
import com.chubu.nagoya.member.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("list.do")
	public ModelAndView list() throws Exception{
		List<BoardVO> list = boardService.listAll();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/board_list");
		mav.addObject("list", list);
		
		return mav;
	}
	
	@RequestMapping(value="write.do", method=RequestMethod.GET)
	public String write() {
		return "board/board_write";
	}
	
	// 02_02. 게시글 작성처리
    @RequestMapping(value="insert.do", method=RequestMethod.POST)
    public String insert(@ModelAttribute BoardVO vo) throws Exception{
        boardService.create(vo);
        return "redirect:list.do";
    }
    
 // 03. 게시글 상세내용 조회, 게시글 조회수 증가 처리
    // @RequestParam : get/post방식으로 전달된 변수 1개
    // HttpSession 세션객체
    @RequestMapping(value="view.do", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int brdNo, HttpSession session) throws Exception{
        // 모델(데이터)+뷰(화면)를 함께 전달하는 객체
        ModelAndView mav = new ModelAndView();
        // 뷰의 이름
        mav.setViewName("board/board_view");
        // 뷰에 전달할 데이터
        mav.addObject("dto", boardService.read(brdNo));
        return mav;
    }

    // 04. 게시글 수정
    // 폼에서 입력한 내용들은 @ModelAttribute BoardVO vo로 전달됨
    @RequestMapping(value="update.do", method=RequestMethod.POST)
    public String update(@ModelAttribute BoardVO vo) throws Exception{
        boardService.update(vo);
        return "redirect:list.do";
    }
    
 // 05. 게시글 삭제
    @RequestMapping(value="delete.do")
    public String delete(@RequestParam int brdNo) throws Exception{
        boardService.delete(brdNo);
        return "redirect:list.do";
    }
	
}
