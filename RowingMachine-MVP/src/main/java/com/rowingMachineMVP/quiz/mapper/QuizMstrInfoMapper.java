package com.rowingMachineMVP.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;

@Mapper
public interface QuizMstrInfoMapper {

	int getQuizTotalCnt(QuizMstrInfoVO quizMstrInfoVO);

	QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO QuizMstrInfoVO);

}
