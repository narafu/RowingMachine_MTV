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

	@RequestMapping(value = { "", "index.do" })
	public String index(Model model) {
		UserInfoVO userVO = new UserInfoVO();
		model.addAttribute("userVO", userVO);
		return "view/index";
	}

	@RequestMapping("quizMain.do")
	public String quizMain(UserInfoVO userVO, Model model) {

		if (StringUtils.isEmpty(userVO.getUserId())) {
			String uuid = UUID.randomUUID().toString();
			userVO.setUserId(uuid);
		}
		userService.insertUserInfo(userVO); // 유저 DB등록
		model.addAttribute("userVO", userVO);

		QuizMstrInfoVO quizMstrInfoVO = new QuizMstrInfoVO();

		if (StringUtils.isEmpty(quizMstrInfoVO.getSubjectTypeCd())) {
			quizMstrInfoVO.setSubjectTypeCd("10"); // 과목코드 셋팅
		}
		if (quizMstrInfoVO.getSrtNo() == 0) {
			quizMstrInfoVO.setSrtNo(1); // 문제번호 셋팅
		}
		quizMstrInfoVO = quizService.selectQuizInfo(quizMstrInfoVO);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		return "view/quizMain";
	}

	@RequestMapping("quizAjax.do")
	public String quizAjax(QuizMstrInfoVO param, Model model) {

		QuizMstrInfoVO quizMstrInfoVO = quizService.selectQuizInfo(param);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		return "view/quizMain :: #quizDiv";
	}
	
	@RequestMapping("quizAnsSave.do")
	@ResponseBody
	public int quizAnsSave(QuizMstrInfoVO param, Model model) {
		
		// 풀이 기록
		int result = quizService.mergeUserAnswer(param);
		
		return result;
	}

	@RequestMapping("quizResult.do")
	public String quizResult(QuizMstrInfoVO param, Model model) {

		List<QuizMstrInfoVO> quizResultList = quizService.selectQuizResultList(param);
		model.addAttribute("quizResultList", quizResultList);

		return "view/quizResult";
	}

	@RequestMapping("statistics.do")
	public String statistics() {

		return "view/statistics";
	}

}
