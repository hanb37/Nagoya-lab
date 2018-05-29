package com.chubu.nagoya.member.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chubu.nagoya.member.model.dto.MemberVO;
import com.chubu.nagoya.member.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService memberService;
	
	@RequestMapping("list.do")
	public ModelAndView memberList() throws Exception{
		List<MemberVO> list = memberService.memberList();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/member_list");
		mav.addObject("list", list);
		
		return mav;
	}
	
	// 02_01 회원 등록 페이지로 이동
    @RequestMapping("write.do")
    public String memberWrite(){
        
        return "member/member_write";
    }
    
    // 02_02 회원 등록 처리 후 ==> 회원목록으로 리다이렉트
    // @ModelAttribute에 폼에서 입력한 데이터가 저장된다.
    @RequestMapping("insert.do")
    // * 폼에서 입력한 데이터를 받아오는 법 3가지 
    //public String memberInsert(HttpServlet request){
    //public String memberInsert(String userId, String userPw, String userName, String userEmail){
    public String memberInsert(@ModelAttribute MemberVO vo){
        // 테이블에 레코드 입력
        memberService.insertMember(vo);
        // * (/)의 유무에 차이
        // /member/list.do : 루트 디렉토리를 기준
        // member/list.do : 현재 디렉토리를 기준
        // member_list.jsp로 리다이렉트
        return "redirect:/member/list.do";
    }
    
    // 03 회원 상세정보 조회
    @RequestMapping(value="view.do", method=RequestMethod.GET)
    public ModelAndView memberView(@RequestParam String userId) throws Exception{
        // 회원 정보를 model에 저장
    	ModelAndView mav = new ModelAndView();
        mav.setViewName("member/member_view");
        // 뷰에 전달할 데이터
        mav.addObject("dto", memberService.viewMember(userId));
        logger.info("클릭한 아이디 : "+userId);
        return mav;
    }
    
 // 04. 회원 정보 수정 처리
    @RequestMapping(value="update.do", method=RequestMethod.POST)
    public String memberUpdate(@ModelAttribute MemberVO vo, Model model){
        // 비밀번호 체크
        boolean result = memberService.checkPw(vo.getUserId(), vo.getUserPw());
        if(result){ // 비밀번호가 일치하면 수정 처리후, 전체 회원 목록으로 리다이렉트
            memberService.updateMember(vo);
            return "redirect:/member/list.do";
        } else { // 비밀번호가 일치하지 않는다면, div에 불일치 문구 출력, viwe.jsp로 포워드
            // 가입일자, 수정일자 저장
            MemberVO vo2 = memberService.viewMember(vo.getUserId());
            vo.setUserRegdate(vo2.getUserRegdate());
            vo.setUserUpdatedate(vo2.getUserUpdatedate());
            model.addAttribute("dto", vo);
            model.addAttribute("message", "비밀번호 불일치");
            return "member/member_view";
        }
    }
 // 05. 회원정보 삭제 처리
    // @RequestMapping : url mapping
    // @RequestParam : get or post방식으로 전달된 변수값
    @RequestMapping("delete.do")
    public String memberDelete(@RequestParam String userId, @RequestParam String userPw, Model model){
        // 비밀번호 체크
        boolean result = memberService.checkPw(userId, userPw);
        if(result){ // 비밀번호가 맞다면 삭제 처리후, 전체 회원 목록으로 리다이렉트
            memberService.deleteMember(userId);
            return "redirect:/member/list.do";
        } else { // 비밀번호가 일치하지 않는다면, div에 불일치 문구 출력, viwe.jsp로 포워드
            model.addAttribute("message", "비밀번호 불일치");
            model.addAttribute("dto", memberService.viewMember(userId));
            return "member/member_view";
        }
    }
}