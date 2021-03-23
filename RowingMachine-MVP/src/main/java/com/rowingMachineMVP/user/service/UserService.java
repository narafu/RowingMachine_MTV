package com.rowingMachineMVP.user.service;

import com.rowingMachineMVP.user.vo.UserInfoVO;

public interface UserService {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(String userId);
}
