package com.RowingMachineMTV.web.index.service;

import java.util.List;

import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;

public interface QuizService {

	QuizMstrInfoVO selectQuizInfo(QuizMstrInfoVO quizVO);

	int mergeUserAnswer(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO);

}
