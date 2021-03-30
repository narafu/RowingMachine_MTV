package com.rowingMachineMVP.quiz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rowingMachineMVP.quiz.mapper.QuizMstrInfoMapper;
import com.rowingMachineMVP.quiz.mapper.QuizUserAnsMapper;
import com.rowingMachineMVP.quiz.service.QuizService;
import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.user.vo.UserVO;

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
	public int mergeSelectQuizAll(UserVO userVO) {
		List<Map<String, String>> mapList = quizUserAnsMapper.selectQuizUsrAnsSeqAll(userVO);
		for (Map<String, String> map : mapList) {
			map.put("userId", userVO.getUserId());
			quizUserAnsMapper.mergeUserAnswer(map);
		}
		return mapList.size();
	}

}
