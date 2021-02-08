package com.rowingMachineMVP.web.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz/modal/")
public class ModalController {

	@RequestMapping("goHomeModal")
	public String goHomeModal() {
		return "view/quiz/modal/goHomeModal";
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
