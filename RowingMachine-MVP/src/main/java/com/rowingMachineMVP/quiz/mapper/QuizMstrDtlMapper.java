package com.rowingMachineMVP.quiz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.quiz.vo.QuizMstrDtlVO;
import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;

@Mapper
public interface QuizMstrDtlMapper {

	List<QuizMstrDtlVO> selectQuizMstrDtlList(QuizMstrInfoVO quizMstrInfoVO);

}
