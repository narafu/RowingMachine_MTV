package com.rowingMachineMVP.quiz.vo;

import com.rowingMachineMVP.common.BaseVO;

import lombok.Data;

@Data
public class QuizMstrDtlVO extends BaseVO {

	private int quizMstrInfoSeq;
	private int quizMstrDtlSeq;
	private int quizUserAnsSeq;
	private int quizUserAnsDtlSeq;
	private int srtNo = 1;
	private String content;
	private String eraseYn = "N";
}
