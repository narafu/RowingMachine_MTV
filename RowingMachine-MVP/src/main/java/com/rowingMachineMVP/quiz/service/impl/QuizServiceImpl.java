package com.rowingMachineMVP.quiz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.groovy.parser.antlr4.util.StringUtils;
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
		
		String userId = param.getUserId();

		if (param.getQuizMstrInfoSeq() == 0) {
			param = quizMstrInfoMapper.getFstQuizInfo();
			if(param == null) {
				return new QuizMstrInfoVO();
			}
			param.setUserId(userId);
		}

		QuizMstrInfoVO quizMstrInfoVO = quizMstrInfoMapper.getQuizInfo(param);

		if (quizMstrInfoVO == null) {
			quizMstrInfoVO = new QuizMstrInfoVO();
			quizMstrInfoVO.setSubjectTypeCd(param.getSubjectTypeCd());
		}

		int quizTotalCnt = quizMstrInfoMapper.getQuizTotalCnt(param);
		quizMstrInfoVO.setQuizTotalCnt(quizTotalCnt);

		List<QuizMstrDtlVO> quizMstrDtlList = quizMstrDtlMapper.selectQuizMstrDtlList(quizMstrInfoVO);
		if (quizMstrDtlList.isEmpty() || param.getSrtNo() == 0) {
			quizMstrDtlList = new ArrayList<QuizMstrDtlVO>();
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
		}

		quizMstrInfoVO.setQuizMstrDtlList(quizMstrDtlList);

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

	@Override
	public List<Map<String, String>> selectQuizNoList(QuizMstrInfoVO quizMstrInfoVO) {
		return quizMstrInfoMapper.selectQuizNoList(quizMstrInfoVO);
	}

	@Override
	public int regQuiz(QuizMstrInfoVO param) {
		int resultCnt = quizMstrInfoMapper.insertQuizMstrInfo(param);
		if (resultCnt > 0) {
			for (QuizMstrDtlVO quizMstrDtlVO : param.getQuizMstrDtlList()) {
				quizMstrDtlVO.setQuizMstrInfoSeq(param.getQuizMstrInfoSeq());
				quizMstrDtlMapper.insertQuizDtlInfo(quizMstrDtlVO);
			}
		}
		return resultCnt;
	}

	@Override
	public int editQuiz(QuizMstrInfoVO param) {
		int resultCnt = quizMstrInfoMapper.updateQuizMstrInfo(param);
		if (resultCnt > 0) {
			for (QuizMstrDtlVO quizMstrDtlVO : param.getQuizMstrDtlList()) {
				quizMstrDtlMapper.updateQuizDtlInfo(quizMstrDtlVO);
			}
		}
		return resultCnt;
	}

	@Override
	public int delQuiz(QuizMstrInfoVO param) {
		return quizMstrInfoMapper.delQuiz(param);
	}

	@Override
	public QuizMstrInfoVO getQuizFormInfo(QuizMstrInfoVO param) {

		QuizMstrInfoVO quizMstrInfoVO = quizMstrInfoMapper.getQuizFormInfo(param);
		if (quizMstrInfoVO == null) {
			quizMstrInfoVO = new QuizMstrInfoVO();
			quizMstrInfoVO.setSubjectTypeCd(param.getSubjectTypeCd());
		}

		int quizTotalCnt = quizMstrInfoMapper.getQuizTotalCnt(param);
		quizMstrInfoVO.setQuizTotalCnt(quizTotalCnt);

		List<QuizMstrDtlVO> quizMstrDtlList = quizMstrDtlMapper.selectQuizMstrDtlList(param);
		if (quizMstrDtlList.isEmpty() || param.getQuizMstrInfoSeq() == 0) {
			quizMstrDtlList = new ArrayList<QuizMstrDtlVO>();
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
			quizMstrDtlList.add(new QuizMstrDtlVO());
		}

		quizMstrInfoVO.setQuizMstrDtlList(quizMstrDtlList);
		return quizMstrInfoVO;
	}

}
