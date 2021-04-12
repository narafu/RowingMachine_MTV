package com.rowingMachineMVP.quiz.vo;

import java.util.List;

import com.rowingMachineMVP.common.BaseVO;

import lombok.Data;

@Data
public class QuizMstrInfoVO extends BaseVO {

	private int quizMstrInfoSeq;
	private String subjectTypeCd = "10";
	private String subjectTypeNm;
	private int srtNo = 1;
	private String content;
	private String answer;
	private String cmntr;

	private int quizUserAnsSeq;
	private String userId;
	private String userAnswer;
	private String answerChk;
	private int takeRev = 1;

	private int secSolving;
	private int minSolving;

	private int quizTotalCnt;
	private int quizTrueCnt;

	private List<QuizMstrDtlVO> quizMstrDtlList;
}
