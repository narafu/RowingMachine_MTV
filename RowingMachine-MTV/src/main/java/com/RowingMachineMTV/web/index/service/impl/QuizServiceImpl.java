package com.RowingMachineMTV.web.index.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RowingMachineMTV.web.index.mapper.QuizMstrInfoMapper;
import com.RowingMachineMTV.web.index.mapper.QuizUserAnsMapper;
import com.RowingMachineMTV.web.index.service.QuizService;
import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizMstrInfoMapper quizMstrInfoMapper;

	@Autowired
	private QuizUserAnsMapper quizUserAnsMapper;

	@Override
	public QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO param) {
		int quizTotalCnt = quizMstrInfoMapper.getQuizTotalCnt(param);
		QuizMstrInfoVO quizMstrInfoVO = quizMstrInfoMapper.getQuizInfo(param);
		quizMstrInfoVO.setQuizTotalCnt(quizTotalCnt);
		return quizMstrInfoVO;
	}

	@Override
	public int mergeUserAnswer(QuizMstrInfoVO quizMstrInfoVO) {
		return quizUserAnsMapper.mergeUserAnswer(quizMstrInfoVO);
	}

	@Override
	public List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO) {
		return quizUserAnsMapper.selectQuizResultList(quizMstrInfoVO);
	}

	@Override
	public List<QuizMstrInfoVO> getQuizCntList(QuizMstrInfoVO param) {
		return quizUserAnsMapper.getQuizCntList(param);
	}

}
