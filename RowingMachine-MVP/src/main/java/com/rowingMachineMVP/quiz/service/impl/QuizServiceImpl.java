package com.rowingMachineMVP.quiz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rowingMachineMVP.quiz.mapper.QuizMstrDtlMapper;
import com.rowingMachineMVP.quiz.mapper.QuizMstrInfoMapper;
import com.rowingMachineMVP.quiz.mapper.QuizUserAnsDtlMapper;
import com.rowingMachineMVP.quiz.mapper.QuizUserAnsMapper;
import com.rowingMachineMVP.quiz.service.QuizService;
import com.rowingMachineMVP.quiz.vo.QuizMstrDtlVO;
import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.user.vo.UserVO;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizMstrInfoMapper quizMstrInfoMapper;
	
	@Autowired
	private QuizMstrDtlMapper quizMstrDtlMapper;

	@Autowired
	private QuizUserAnsMapper quizUserAnsMapper;
	
	@Autowired
	private QuizUserAnsDtlMapper quizUserAnsDtlMapper;

	@Override
	public QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO param) {
		int quizTotalCnt = quizMstrInfoMapper.getQuizTotalCnt(param);
		QuizMstrInfoVO quizMstrInfoVO = quizMstrInfoMapper.getQuizInfo(param);
		quizMstrInfoVO.setQuizTotalCnt(quizTotalCnt);
		quizMstrInfoVO.setQuizMstrDtlList(quizMstrDtlMapper.selectQuizMstrDtlList(quizMstrInfoVO));
		return quizMstrInfoVO;
	}

	@Override
	public int updateUserAnswer(QuizMstrInfoVO param) {
		int resultCnt = 0;
		int quizUserAnsSeq = param.getQuizUserAnsSeq();
		resultCnt = quizUserAnsMapper.updateUserAnswer(param);
		if (resultCnt > 0) {
			List<QuizMstrDtlVO> quizMstrDtlList = param.getQuizMstrDtlList();
			for (QuizMstrDtlVO quizMstrDtlVO : quizMstrDtlList) {
				quizMstrDtlVO.setQuizUserAnsSeq(quizUserAnsSeq);
				quizUserAnsDtlMapper.mergeUserAnswerDtl(quizMstrDtlVO);
			}
		}
		return resultCnt;
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
	public int mergeSelectQuizAll(UserVO userVO) {
		List<Map<String, String>> mapList = quizUserAnsMapper.selectQuizUsrAnsSeqAll(userVO);
		for (Map<String, String> map : mapList) {
			map.put("userId", userVO.getUserId());
			quizUserAnsMapper.mergeUserAnswer(map);
		}
		return mapList.size();
	}

}
