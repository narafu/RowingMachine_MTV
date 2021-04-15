package com.rowingMachineMVP.quiz.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rowingMachineMVP.common.BaseUtil;
import com.rowingMachineMVP.quiz.service.QuizService;
import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.user.service.UserService;
import com.rowingMachineMVP.user.vo.UserVO;

@Controller
@RequestMapping("/")
public class QuizController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private BaseUtil baseUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private QuizService quizService;

	@GetMapping(value = { "", "quiz/index.do" })
	public String indexGet(Model model) {
		UserVO userVO = new UserVO();
		model.addAttribute("userVO", userVO);
		return "view/quiz/index";
	}

	@PostMapping(value = { "quiz/index.do" })
	public String indexPost(HttpServletRequest req, UserVO param) {

		// 유저 조회
		UserVO userVO = userService.getUserInfo(param.getUserId());
		if (userVO == null) {
			// 유저 DB등록
			userService.insertUserInfo(param);
			userVO = param;
		}

		// 문제 맵핑
		quizService.mergeSelectQuizAll(userVO);

		// 세션 등록
		HttpSession httpSession = req.getSession();
		httpSession.setAttribute("userVO", userVO);

		return "redirect:/quiz/main.do";
	}

	@RequestMapping("quiz/main.do")
	public String quizMain(QuizMstrInfoVO param, Model model) {

		UserVO userVO = (UserVO) httpSession.getAttribute("userVO");
		model.addAttribute("userVO", userVO);

		// 공통코드(과목코드)
		List<Map<String, String>> cnmmCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("cnmmCdList", cnmmCdList);

		// 퀴즈문제 조회
		param.setUserId(userVO.getUserId());
		QuizMstrInfoVO quizMstrInfoVO = quizService.getQuizInfo(param);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		if (quizMstrInfoVO.getQuizMstrInfoSeq() == 0) {
			if ("ADMIN".equals(userVO.getRoleCd())) {
				return "redirect:/quiz/admin/quizForm.do";
			} else {
				return "view/error";
			}
		}

		// 결과 리스트
		List<QuizMstrInfoVO> quizResultList = quizService.selectQuizResultList(param);
		model.addAttribute("quizResultList", quizResultList);

		return "view/quiz/main";
	}

	@RequestMapping("quiz/quizAjax.do")
	public String quizAjax(QuizMstrInfoVO param, Model model) {

		// 공통코드(과목코드)
		List<Map<String, String>> cnmmCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("cnmmCdList", cnmmCdList);

		// 퀴즈문제 조회
		QuizMstrInfoVO quizMstrInfoVO = quizService.getQuizInfo(param);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		// 결과 리스트
		List<QuizMstrInfoVO> quizResultList = quizService.selectQuizResultList(param);
		model.addAttribute("quizResultList", quizResultList);

		return "view/quiz/main :: #quizDiv";
	}

	@RequestMapping("quiz/resultQuizStasticsAjax.do")
	public String resultQuizStasticsAjax(QuizMstrInfoVO param, Model model) {

		// 공통코드(과목코드)
		List<Map<String, String>> cnmmCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("cnmmCdList", cnmmCdList);

		// 퀴즈문제 조회
		QuizMstrInfoVO quizMstrInfoVO = quizService.getQuizInfo(param);
		model.addAttribute("quizMstrInfoVO", quizMstrInfoVO);

		return "view/quiz/resultQuizStastics";
	}

	@RequestMapping("quiz/sidebarAjax.do")
	public String sidebarAjax(QuizMstrInfoVO param, Model model) {

		// 공통코드(과목코드)
		List<Map<String, String>> cnmmCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("cnmmCdList", cnmmCdList);

		// 결과 리스트
		List<QuizMstrInfoVO> quizResultList = quizService.selectQuizResultList(param);
		model.addAttribute("quizResultList", quizResultList);

		return "view/quiz/sidebar/quizNav";
	}

	@RequestMapping("quiz/answer.do")
	@ResponseBody
	public int quizAnsSave(QuizMstrInfoVO param, Model model) {
		// 유저 문제풀이 기록
		int result = quizService.updateUserAnswer(param);
		return result;
	}

	@RequestMapping("quiz/result.do")
	public String quizResult(QuizMstrInfoVO param, Model model) {
		return "view/quiz/result";
	}

	@RequestMapping("quiz/statistics.do")
	public String statistics(QuizMstrInfoVO param, Model model) {

		// 공통코드(과목코드)
		List<Map<String, String>> cnmmCdList = baseUtil.getCnmmCdList("001");
		model.addAttribute("cnmmCdList", cnmmCdList);

		// 결과 리스트
		List<QuizMstrInfoVO> quizResultList = quizService.selectQuizResultList(param);
		model.addAttribute("quizResultList", quizResultList);

		// 갯수 리스트
		List<QuizMstrInfoVO> quizCntList = quizService.getQuizCntList(param);
		model.addAttribute("quizCntList", quizCntList);

		return "view/quiz/statistics";
	}

	@RequestMapping("quiz/popup/print/certificate.do")
	public String certificatePrintPopup(Model model) {
		UserVO userVO = (UserVO) httpSession.getAttribute("userVO");
		model.addAttribute("userVO", userVO);
		return "view/quiz/popup/certificatePrintPopup";
	}
}
