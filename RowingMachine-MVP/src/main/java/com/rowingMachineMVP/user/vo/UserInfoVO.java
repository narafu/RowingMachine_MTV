package com.rowingMachineMVP.user.vo;

import com.rowingMachineMVP.common.BaseVO;

import lombok.Data;

@Data
public class UserInfoVO extends BaseVO {

	private int userInfoSeq;
	private String userId;
	private String userNm;
	private String password;
	
	private int takeRev;
}
