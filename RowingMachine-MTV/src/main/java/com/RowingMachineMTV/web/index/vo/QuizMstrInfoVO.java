package com.RowingMachineMTV.web.index.vo;

import lombok.Data;

@Data
public class QuizMstrInfoVO extends BaseVO {

	private int quizMstrInfoSeq;
	private String subjectTypeCd;
	private String subjectTypeNm;
	private int srtNo;
	private String content;
	private String answer;

	private int quizUserAnsSeq;
	private String userId;
	private String userAnswer;
	private String answerChk;
	private int takeRev;
	
	private int secSolving;
	private int minSolving;
	
	private int quizTotalCnt;
	private int quizTrueCnt;
}
