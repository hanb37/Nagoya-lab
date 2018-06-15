package com.chubu.nagoya.member.controller;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.chubu.nagoya.member.model.dto.MessageInfo;
import com.chubu.nagoya.member.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("list.do")
	public ModelAndView list() throws Exception{
		// display the date 
		Date date = new Date();	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String dateJapan = sdf.format(date);
		
		//list by date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate =  dateFormat.parse(dateFormat.format(date));
		List<BoardVO> list = boardService.listAll(myDate);
		List<MessageInfo> listMessages = boardService.listMessages(myDate);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/board_list");
		mav.addObject("list", list);
		mav.addObject("date", dateJapan);
		mav.addObject("listMessages", listMessages);
		
		return mav;
	}
	
	@RequestMapping(value = "next.do", method = RequestMethod.GET)
	public ModelAndView next(@RequestParam String day) throws Exception{
		
		Date date = new Date();	
		SimpleDateFormat dateFormatJP = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if (day.contains("back")) {
			String dayCur = day.replaceAll("back", "");
			//decrease date
			Date dateCurrent = dateFormatJP.parse(dayCur);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(dateCurrent); 
			cal.add(Calendar.DATE, -1);
			date = cal.getTime();
			
		} else {
			String dayCur = day.replaceAll("next", "");
			//increase date
			Date dateCurrent = dateFormatJP.parse(dayCur);
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(dateCurrent); 
			cal.add(Calendar.DATE, 1);
			date = cal.getTime();
		}

		// format Japan date
		String dateJapan = dateFormatJP.format(date);
		
		Date dateCur =  dateFormat.parse(dateFormat.format(date));
		//get list by date current
		List<BoardVO> list = boardService.listAll(dateCur);
		
		Timestamp timestamp = Timestamp.valueOf("2018-06-08 16:00:00");
		List<MessageInfo> listMessages = boardService.listMessages(timestamp);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/board_list");
		mav.addObject("list", list);
		mav.addObject("date", dateJapan);
		mav.addObject("listMessages", listMessages);
		
		return mav;
	}
	
	@RequestMapping(value="write.do", method=RequestMethod.GET)
	public String write() {
		return "board/board_write";
	}
	
	// 02_02. 投稿処理
    @RequestMapping(value="insert.do", method=RequestMethod.POST)
    public String insert(@ModelAttribute BoardVO vo) throws Exception{
        boardService.create(vo);
        return "redirect:list.do";
    }
    
 // 03. 詳細内容ページ
    // @RequestParam : get/post方式で伝送される変数1個
    // HttpSession セッションオブジェクト
    @RequestMapping(value="view.do", method=RequestMethod.GET)
    public ModelAndView view(@RequestParam int brdNo, HttpSession session) throws Exception{
        // モデル（データ）+ビュー(画面）を一緒に伝送するオブジェクト
        ModelAndView mav = new ModelAndView();
        // ビュー名
        mav.setViewName("board/board_view");
        // ビューに伝送するデータ
        mav.addObject("dto", boardService.read(brdNo));
        return mav;
    }

    // 04. 投稿内容を編集
    // Formから入力した内容は @ModelAttribute BoardVO voへ伝送
    @RequestMapping(value="update.do", method=RequestMethod.POST)
    public String update(@ModelAttribute BoardVO vo) throws Exception{
        boardService.update(vo);
        return "redirect:list.do";
    }
    
 // 05. 投稿内容を削除
    @RequestMapping(value="delete.do")
    public String delete(@RequestParam int brdNo) throws Exception{
        boardService.delete(brdNo);
        return "redirect:list.do";
    }
	
}
