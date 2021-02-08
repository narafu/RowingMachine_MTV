package com.rowingMachineMVP.web.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.web.quiz.vo.QuizMstrInfoVO;

@Mapper
public interface QuizMstrInfoMapper {

	int getQuizTotalCnt(QuizMstrInfoVO quizMstrInfoVO);

	QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO QuizMstrInfoVO);

}
