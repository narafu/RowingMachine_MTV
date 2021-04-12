package com.rowingMachineMVP.quiz.vo;

import lombok.Data;

@Data
public class QuizMstrDtlVO {

	private int quizMstrInfoSeq;
	private int quizMstrDtlSeq;
	private int quizUserAnsSeq;
	private int quizUserAnsDtlSeq;
	private int srtNo = 1;
	private String content;
	private String eraseYn = "N";
}
