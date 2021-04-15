package com.rowingMachineMVP.quiz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;

@Mapper
public interface QuizMstrInfoMapper {

	int getQuizTotalCnt(QuizMstrInfoVO quizMstrInfoVO);

	QuizMstrInfoVO getQuizInfo(QuizMstrInfoVO QuizMstrInfoVO);

	List<Map<String, String>> selectQuizNoList(QuizMstrInfoVO quizMstrInfoVO);

	int insertQuizMstrInfo(QuizMstrInfoVO param);

	int updateQuizMstrInfo(QuizMstrInfoVO param);

	int delQuiz(QuizMstrInfoVO param);

	QuizMstrInfoVO getQuizFormInfo(QuizMstrInfoVO param);

	QuizMstrInfoVO getFstQuizInfo();

}
