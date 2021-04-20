package com.rowingMachineMVP.quiz.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rowingMachineMVP.common.BaseUtil;
import com.rowingMachineMVP.quiz.service.QuizService;
import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;

@Controller
@RequestMapping("/quiz/admin/")
public class AdminController {

	@Autowired
	private BaseUtil baseUtil;

	@Autowired
	private QuizService quizService;

	@RequestMapping("quizForm.do")
	public String quizForm(Model model) {

		// 연도 리스트
		int curYear = LocalDate.now().getYear();
		int[] examYearArr = new int[5];
		for (int i = 0; i < examYearArr.length; i++) {
			examYearArr[i] = curYear - i;
		}
		model.addAttribute("examYearArr", examYearArr);

		// 공통코드(시험구분 코드)
		List<Map<String, String>> examCdList = baseUtil.getCnmmCdList("002");
		model.addAttribute("examCdList", examCdList);

		// 시험회차 리스트
		int[] examNoArr = new int[10];
		for (int i = 0; i < examNoArr.length; i++) {
			examNoArr[i] = i + 1;
		}
		model.addAttribute("examNoArr", examNoArr);

		// 공통코드(과목코드)
		List<Map<String, String>> subjectCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("subjectCdList", subjectCdList);

		// 문제 조회
		QuizMstrInfoVO quizMstrInfoVO = quizService.getQuizFormInfo(new QuizMstrInfoVO());
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		// 문제리스트
		List<Map<String, String>> quizNoList = quizService.selectQuizNoList(quizMstrInfoVO);
		model.addAttribute("quizNoList", quizNoList);

		return "view/quiz/admin/quizForm";
	}

	@RequestMapping("quizFormAjax.do")
	public String quizFormAjax(QuizMstrInfoVO param, Model model) {

		// 연도 리스트
		int curYear = LocalDate.now().getYear();
		int[] examYearArr = new int[5];
		for (int i = 0; i < examYearArr.length; i++) {
			examYearArr[i] = curYear - i;
		}
		model.addAttribute("examYearArr", examYearArr);

		// 공통코드(시험구분 코드)
		List<Map<String, String>> examCdList = baseUtil.getCnmmCdList("002");
		model.addAttribute("examCdList", examCdList);

		// 시험회차 리스트
		int[] examNoArr = new int[10];
		for (int i = 0; i < examNoArr.length; i++) {
			examNoArr[i] = i + 1;
		}
		model.addAttribute("examNoArr", examNoArr);

		// 공통코드(과목코드)
		List<Map<String, String>> subjectCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("subjectCdList", subjectCdList);

		// 문제 조회
		QuizMstrInfoVO quizMstrInfoVO = quizService.getQuizFormInfo(param);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		// 문제리스트
		List<Map<String, String>> quizNoList = quizService.selectQuizNoList(param);
		model.addAttribute("quizNoList", quizNoList);

		return "view/quiz/admin/quizForm :: #adminForm";
	}

	@RequestMapping("regQuiz.do")
	@ResponseBody
	public String regQuiz(QuizMstrInfoVO param) {
		int resultCnt = quizService.regQuiz(param);
		String result = "";
		if (resultCnt > 0) {
			result = "등록되었습니다.";
		} else {
			result = "오류가 발생하였습니다.";
		}
		return result;
	}

	@RequestMapping("editQuiz.do")
	@ResponseBody
	public String editQuiz(QuizMstrInfoVO param) {
		int resultCnt = quizService.editQuiz(param);
		String result = "";
		if (resultCnt > 0) {
			result = "수정하였습니다.";
		} else {
			result = "오류가 발생하였습니다.";
		}
		return result;
	}

	@RequestMapping("delQuiz.do")
	@ResponseBody
	public String delQuiz(QuizMstrInfoVO param) {
		int resultCnt = quizService.delQuiz(param);
		String result = "";
		if (resultCnt > 0) {
			result = "삭제되었습니다.";
		} else {
			result = "오류가 발생하였습니다.";
		}
		return result;
	}
}
