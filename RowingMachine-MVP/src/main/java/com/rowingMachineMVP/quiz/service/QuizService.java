package com.rowingMachineMVP.quiz.service;

import java.util.List;
import java.util.Map;

import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.user.vo.UserVO;

public interface QuizService {

	QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO quizVO);

	List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> getQuizCntList(QuizMstrInfoVO param);

	int updateUserAnswer(QuizMstrInfoVO param);

	int mergeSelectQuizAll(UserVO userVO);

	int regQuiz(QuizMstrInfoVO param);

	int editQuiz(QuizMstrInfoVO param);

	List<Map<String, String>> selectQuizNoList(QuizMstrInfoVO quizMstrInfoVO);

	int delQuiz(QuizMstrInfoVO param);

	QuizMstrInfoVO getQuizFormInfo(QuizMstrInfoVO quizMstrInfoVO);

}
