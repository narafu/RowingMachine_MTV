package com.rowingMachineMVP.quiz.service;

import java.util.List;

import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.user.vo.UserInfoVO;

public interface QuizService {

	QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO quizVO);

	List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> getQuizCntList(QuizMstrInfoVO param);

	int updateUserAnswer(QuizMstrInfoVO param);

	int mergeSelectQuizAll(UserInfoVO userVO);

}
