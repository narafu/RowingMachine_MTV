package com.RowingMachineMTV.web.index.controller;

import java.util.List;
import java.util.UUID;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RowingMachineMTV.web.index.service.QuizService;
import com.RowingMachineMTV.web.index.service.UserService;
import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;
import com.RowingMachineMTV.web.index.vo.UserInfoVO;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private UserService userService;

	@Autowired
	private QuizService quizService;

	@RequestMapping(value = { "", "quiz/index.do" })
	public String index(Model model) {
		UserInfoVO userVO = new UserInfoVO();
		model.addAttribute("userVO", userVO);
		return "view/quiz/index";
	}

	@RequestMapping("quiz/main.do")
	public String quizMain(UserInfoVO param, Model model) {
		
		String userId = param.getUserId();
		
		if (StringUtils.isEmpty(userId)) {
			String uuid = UUID.randomUUID().toString();
			param.setUserId(uuid);
			param.setUserNm("guest");
		} else {
			String[] userNm = userId.split("@");
			param.setUserNm(userNm[0]);
		}
		
		UserInfoVO userVO = userService.getUserInfo(param); // 유저 조회
		if(userVO == null) {
			userService.insertUserInfo(param); // 유저 DB등록
		}
		
		model.addAttribute("userVO", param);

		QuizMstrInfoVO quizMstrInfoVO = new QuizMstrInfoVO();

		// 과목코드 셋팅
		if (StringUtils.isEmpty(quizMstrInfoVO.getSubjectTypeCd())) {
			quizMstrInfoVO.setSubjectTypeCd("10");
		}

		// 문제번호 셋팅
		if (quizMstrInfoVO.getSrtNo() == 0) {
			quizMstrInfoVO.setSrtNo(1);
		}

		quizMstrInfoVO = quizService.getQuizInfo(quizMstrInfoVO);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		return "view/quiz/main";
	}

	@RequestMapping("quiz/answer.do")
	@ResponseBody
	public int quizAnsSave(QuizMstrInfoVO param, Model model) {
		// 유저 문제풀이 기록
		int result = quizService.mergeUserAnswer(param);
		return result;
	}

	@RequestMapping("quiz/quizAjax.do")
	public String quizAjax(QuizMstrInfoVO param, Model model) {
		QuizMstrInfoVO quizMstrInfoVO = quizService.getQuizInfo(param);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);
		return "view/quiz/main :: #quizDiv";
	}

	@RequestMapping("quiz/result.do")
	public String quizResult(QuizMstrInfoVO param, Model model) {
		model.addAttribute("quizMstrInfoVO", param);
		return "view/quiz/result";
	}

	@RequestMapping("quiz/statistics.do")
	public String statistics(QuizMstrInfoVO param, Model model) {
		List<QuizMstrInfoVO> quizResultList = quizService.selectQuizResultList(param);
		model.addAttribute("quizResultList", quizResultList);
		List<QuizMstrInfoVO> quizCntList = quizService.getQuizCntList(param);
		model.addAttribute("quizCntList", quizCntList);
		return "view/quiz/statistics";
	}

}
