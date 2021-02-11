package com.RowingMachineMTV.web.index.controller;

import java.util.UUID;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.RowingMachineMTV.web.index.service.QuizService;
import com.RowingMachineMTV.web.index.service.UserService;
import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;
import com.RowingMachineMTV.web.index.vo.UserQuizVO;
import com.RowingMachineMTV.web.index.vo.UserInfoVO;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;

	@Autowired
	private QuizService quizService;

	@RequestMapping(value = { "", "index.do" })
	public String index(Model model) {
		UserQuizVO userQuizVO = new UserQuizVO();
		model.addAttribute("userQuizVO", userQuizVO);
		return "view/index";
	}

	@RequestMapping("quiz-main.do")
	public String quizMain(UserInfoVO userVO, QuizMstrInfoVO param, Model model) {

		QuizMstrInfoVO quizMstrInfoVO = param;
		
		if (StringUtils.isEmpty(userVO.getUserId())) {
			String uuid = UUID.randomUUID().toString();
			userVO.setUserId(uuid);
		}
		userService.insertUserInfo(userVO);
		model.addAttribute("userVO", userVO);

		if (StringUtils.isEmpty(quizMstrInfoVO.getSubjectTypeCd())) {
			quizMstrInfoVO.setSubjectTypeCd("10");
		}
		if (quizMstrInfoVO.getSrtNo() == 0) {
			quizMstrInfoVO.setSrtNo(1);
		}
		quizMstrInfoVO = quizService.selectQuizInfo(quizMstrInfoVO);
		model.addAttribute("quizVO", quizMstrInfoVO);

		UserQuizVO userQuizVO = new UserQuizVO();
		model.addAttribute("userQuizVO", userQuizVO);

		return "view/quiz-main";
	}

	@RequestMapping("quiz-ajax.do")
	public String quizAjax(UserQuizVO userQuizVO, QuizMstrInfoVO param, Model model) {

		quizService.insertUserAnswer(userQuizVO);

		int quizTotalCnt = param.getQuizTotalCnt();
		int srtNo = param.getSrtNo();

		if (quizTotalCnt > srtNo) {
			QuizMstrInfoVO quizVO = quizService.selectQuizInfo(param);
			model.addAttribute("quizVO", quizVO);

			return "view/quiz-main :: #quizDiv";
			
		} else {

			return "redirect: /quiz-result.do";
		}
	}

	@RequestMapping("quiz-result.do")
	public String quizResult() {

		return "view/quiz-result";
	}

	@RequestMapping("statistics.do")
	public String statistics() {

		return "view/statistics";
	}

}
