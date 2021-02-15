package com.RowingMachineMTV.web.index.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;

@Mapper
public interface QuizUserAnsMapper {

	int getQuizTotalCnt(QuizMstrInfoVO param);

	int mergeUserAnswer(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO);

}
