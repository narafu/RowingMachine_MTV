package com.rowingMachineMVP.web.quiz.service;

import com.rowingMachineMVP.web.quiz.vo.UserInfoVO;

public interface UserService {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(String userId);
}
