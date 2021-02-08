package com.rowingMachineMVP.web.quiz.vo;

import com.rowingMachineMVP.web.common.BaseVO;

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
}
