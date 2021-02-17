package com.RowingMachineMTV.web.index.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;

@Mapper
public interface QuizMstrInfoMapper {

	int getQuizTotalCnt(QuizMstrInfoVO quizMstrInfoVO);

	QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO QuizMstrInfoVO);

}
