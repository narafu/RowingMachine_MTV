package com.rowingMachineMVP.web.quiz.vo;

import com.rowingMachineMVP.web.common.BaseVO;

import lombok.Data;

@Data
public class UserInfoVO extends BaseVO {

	private int userInfoSeq;
	private String userId;
	private String userNm;
	private String password;
	
	private int takeRev;
}
