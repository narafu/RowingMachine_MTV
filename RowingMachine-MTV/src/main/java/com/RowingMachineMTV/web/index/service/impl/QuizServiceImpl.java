package com.RowingMachineMTV.web.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RowingMachineMTV.web.index.mapper.QuizMstrInfoMapper;
import com.RowingMachineMTV.web.index.mapper.QuizUserAnsMapper;
import com.RowingMachineMTV.web.index.service.QuizService;
import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;
import com.RowingMachineMTV.web.index.vo.UserQuizVO;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizMstrInfoMapper quizMstrInfoMapper;

	@Autowired
	private QuizUserAnsMapper quizUserAnsMapper;

	@Override
	public QuizMstrInfoVO selectQuizInfo(QuizMstrInfoVO quizMstrInfoVO) {
		int quizTotalCnt = quizMstrInfoMapper.getQuizTotalCnt(quizMstrInfoVO);
		quizMstrInfoVO = quizMstrInfoMapper.selectQuizInfo(quizMstrInfoVO);
		quizMstrInfoVO.setQuizMstrInfoSeq(quizTotalCnt);
		return quizMstrInfoVO;
	}

	@Override
	public int insertUserAnswer(UserQuizVO userQuizVO) {
		return quizUserAnsMapper.insertUserAnswer(userQuizVO);
	}

}
