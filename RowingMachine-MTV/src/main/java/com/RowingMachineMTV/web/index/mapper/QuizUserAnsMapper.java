package com.RowingMachineMTV.web.index.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.RowingMachineMTV.web.index.vo.QuizMstrInfoVO;
import com.RowingMachineMTV.web.index.vo.UserQuizVO;

@Mapper
public interface QuizUserAnsMapper {

	int getQuizTotalCnt(QuizMstrInfoVO param);

	QuizMstrInfoVO selectQuizInfo(QuizMstrInfoVO param);

	int insertUserAnswer(UserQuizVO userQuizVO);

}
