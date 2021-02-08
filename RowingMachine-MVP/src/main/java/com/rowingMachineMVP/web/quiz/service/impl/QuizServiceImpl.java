package com.rowingMachineMVP.web.quiz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rowingMachineMVP.web.quiz.mapper.QuizMstrInfoMapper;
import com.rowingMachineMVP.web.quiz.mapper.QuizUserAnsMapper;
import com.rowingMachineMVP.web.quiz.service.QuizService;
import com.rowingMachineMVP.web.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.web.quiz.vo.UserInfoVO;

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
	public int updateUserAnswer(QuizMstrInfoVO quizMstrInfoVO) {
		return quizUserAnsMapper.updateUserAnswer(quizMstrInfoVO);
	}

	@Override
	public List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO) {
		return quizUserAnsMapper.selectQuizResultList(quizMstrInfoVO);
	}

	@Override
	public List<QuizMstrInfoVO> getQuizCntList(QuizMstrInfoVO param) {
		return quizUserAnsMapper.getQuizCntList(param);
	}

	@Override
	public int insertSelectQuizAll(UserInfoVO userVO) {
		List<Integer> seqList = quizUserAnsMapper.selectQuizMstrInfoSeqAll();
		for (int seq : seqList) {
			QuizMstrInfoVO quizMstrInfoVO = new QuizMstrInfoVO();
			quizMstrInfoVO.setQuizMstrInfoSeq(seq);
			quizMstrInfoVO.setUserId(userVO.getUserId());
			quizUserAnsMapper.insertUserAnswer(quizMstrInfoVO);
		}
		return seqList.size();
	}	

	@Override
	public List<Integer> selectQuizMstrInfoSeqAll() {
		return quizUserAnsMapper.selectQuizMstrInfoSeqAll();
	}

}
