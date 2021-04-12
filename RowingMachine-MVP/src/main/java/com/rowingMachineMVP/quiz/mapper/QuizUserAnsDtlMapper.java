package com.rowingMachineMVP.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.quiz.vo.QuizMstrDtlVO;

@Mapper
public interface QuizUserAnsDtlMapper {

	int mergeUserAnswerDtl(QuizMstrDtlVO quizMstrDtlVO);

}
