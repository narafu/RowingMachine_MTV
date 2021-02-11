package com.RowingMachineMTV.web.index.service;

import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;
import com.RowingMachineMTV.web.index.vo.UserQuizVO;

public interface QuizService {

	QuizMstrInfoVO selectQuizInfo(QuizMstrInfoVO quizVO);

	int insertUserAnswer(UserQuizVO userQuizVO);

}
