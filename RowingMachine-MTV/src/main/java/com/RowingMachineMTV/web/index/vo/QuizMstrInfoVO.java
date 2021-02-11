package com.RowingMachineMTV.web.index.vo;

import lombok.Data;

@Data
public class QuizMstrInfoVO extends BaseVO {

	private int quizMstrInfoSeq;
	private String subjectTypeCd;
	private int srtNo;
	private String content;
	private String answer;

	private int quizTotalCnt;
}
