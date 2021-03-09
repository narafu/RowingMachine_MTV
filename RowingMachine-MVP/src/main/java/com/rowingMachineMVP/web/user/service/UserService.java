package com.rowingMachineMVP.web.user.service;

import com.rowingMachineMVP.web.user.vo.UserInfoVO;

public interface UserService {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(String userId);
}
