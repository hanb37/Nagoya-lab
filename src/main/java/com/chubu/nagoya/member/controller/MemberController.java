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

	// 01. 会員リストページへ移動
	@RequestMapping("list.do")
	public ModelAndView memberList() throws Exception {
		List<MemberVO> list = memberService.memberList();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/member_list");
		mav.addObject("list", list);

		return mav;
	}

	// 02_01 会員登録ページへ移動
	@RequestMapping("write.do")
	public String memberWrite() {

		return "member/member_write";
	}

	// 02_02 会員登録処理した後 ==> 会員リストにリダイレクト
	// @ModelAttributeにFormから入力したデータが保存
	@RequestMapping("insert.do")
	// * Formから入力したデータを受け取る３つの方法
	// public String memberInsert(HttpServlet request){
	// public String memberInsert(String userId, String userPw, String userName,
	// String userEmail){
	public String memberInsert(@ModelAttribute MemberVO vo) {
		// Tableにレコードを入力
		memberService.insertMember(vo);
		// * (/)の有り無しとの違い
		// /member/list.do : ルートディレクトリを基づく
		// member/list.do : 現在のディレクトリを基づく
		// member_list.jspへリダイレクト
		return "redirect:/member/list.do";
	}

	// 03 会員情報を詳細照会
	@RequestMapping(value = "view.do", method = RequestMethod.GET)
	public ModelAndView memberView(@RequestParam String userId) throws Exception {
		// 会員情報をModelに保存
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/member_view");
		// ビューに伝送するデータ
		mav.addObject("dto", memberService.viewMember(userId));
		logger.info("クリックしたID : " + userId);
		return mav;
	}

	// 04. 会員情報を編集
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	public String memberUpdate(@ModelAttribute MemberVO vo, Model model) {
		// パスワードチェック
		boolean result = memberService.checkPw(vo.getUserId(), vo.getUserPw());
		if (result) { // パスワードが一致すれば編集処理した後、会員リストにリダイレクト
			memberService.updateMember(vo);
			return "redirect:/member/list.do";
		} else { // パスワードが一致しなければdivに不一致メッセージを出力view.jspへフォワード
			// 登録日付、編集日付を保存
			MemberVO vo2 = memberService.viewMember(vo.getUserId());
			vo.setUserRegdate(vo2.getUserRegdate());
			vo.setUserUpdatedate(vo2.getUserUpdatedate());
			model.addAttribute("dto", vo);
			model.addAttribute("message", "パスワードが不一致");
			return "member/member_view";
		}
	}

	// 05. 会員情報削除を処理
	// @RequestMapping : url mapping
	// @RequestParam : get or post方式で伝送された変数の値
	@RequestMapping("delete.do")
	public String memberDelete(@RequestParam String userId, @RequestParam String userPw, Model model) {
		// パスワードをチェック
		boolean result = memberService.checkPw(userId, userPw);
		if (result) { // パスワードが一致すれば削除処理した後、会員リストにリダイレクト
			memberService.deleteMember(userId);
			return "redirect:/member/list.do";
		} else { // パスワードが一致しなければdivに不一致メッセージを出力view.jspへフォワード
			model.addAttribute("message", "パスワードが一致していません。");
			model.addAttribute("dto", memberService.viewMember(userId));
			return "member/member_view";
		}
	}
}