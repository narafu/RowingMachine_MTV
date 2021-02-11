package com.RowingMachineMTV.web.index.vo;

import lombok.Data;

@Data
public class UserQuizVO extends BaseVO {

	private int quizUserAnsSeq;
	private int quizMstrInfoSeq;
	private String userId;
	private String userAnswer;
	private String answerChk;
	private int takeRev;
}
