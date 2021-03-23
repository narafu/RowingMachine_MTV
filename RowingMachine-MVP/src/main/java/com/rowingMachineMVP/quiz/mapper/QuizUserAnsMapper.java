package com.rowingMachineMVP.quiz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.quiz.vo.QuizMstrInfoVO;
import com.rowingMachineMVP.user.vo.UserInfoVO;

@Mapper
public interface QuizUserAnsMapper {

	List<QuizMstrInfoVO> selectQuizResultList(QuizMstrInfoVO quizMstrInfoVO);

	List<QuizMstrInfoVO> getQuizCntList(QuizMstrInfoVO param);

	List<Map<String, String>> selectQuizUsrAnsSeqAll(UserInfoVO userVO);

	int updateUserAnswer(QuizMstrInfoVO quizMstrInfoVO);

	int mergeUserAnswer(Map<String, String> map);

}
