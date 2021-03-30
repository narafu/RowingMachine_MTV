package com.rowingMachineMVP.quiz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rowingMachineMVP.user.vo.UserVO;

@Controller
@RequestMapping("/quiz/modal/")
public class ModalController {

	@Autowired
	private HttpSession httpSession;

	@RequestMapping("loginModal")
	public String loginModal() {
		return "view/quiz/modal/loginModal";
	}

	@RequestMapping("goHomeModal")
	public String goHomeModal() {
		return "view/quiz/modal/goHomeModal";
	}

	@RequestMapping("invitationModal")
	public String invitationModal(Model model) {
		UserVO session = (UserVO) httpSession.getAttribute("userVO");
		model.addAttribute("userVO", session);
		return "view/quiz/modal/invitationModal";
	}

	@RequestMapping("firstPageModal")
	public String firstPageModal(int srtNo, Model model) {
		model.addAttribute("srtNo", srtNo);
		return "view/quiz/modal/firstPageModal";
	}

	@RequestMapping("lastPageModal")
	public String lastPageModal(int srtNo, Model model) {
		model.addAttribute("srtNo", srtNo);
		return "view/quiz/modal/lastPageModal";
	}

	@RequestMapping("quizNullModal")
	public String quizNullModal(int srtNo, Model model) {
		model.addAttribute("srtNo", srtNo);
		return "view/quiz/modal/quizNullModal";
	}

	@RequestMapping("resultPageModal")
	public String resultPageModal(int srtNo, Model model) {
		model.addAttribute("srtNo", srtNo);
		return "view/quiz/modal/resultPageModal";
	}
}
