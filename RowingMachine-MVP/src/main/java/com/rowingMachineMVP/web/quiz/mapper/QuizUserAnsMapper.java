package com.rowingMachineMVP.web.quiz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.web.quiz.vo.QuizMstrInfoVO;

@Mapper
public interface QuizUserAnsMapper {

	int getQuizTotalCnt(QuizMstrInfoVO param);

	int insertUserAnswer(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> getQuizCntList(QuizMstrInfoVO param);

	List<Integer> selectQuizMstrInfoSeqAll();

	int updateUserAnswer(QuizMstrInfoVO quizMstrInfoVO);

}
