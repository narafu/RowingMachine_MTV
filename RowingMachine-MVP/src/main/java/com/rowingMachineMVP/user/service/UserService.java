package com.rowingMachineMVP.user.service;

import com.rowingMachineMVP.user.vo.UserVO;

public interface UserService {

	int insertUserInfo(UserVO userVO);

	UserVO getUserInfo(String userId);
}
