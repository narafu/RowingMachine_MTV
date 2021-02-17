package com.RowingMachineMTV.web.index.service;

import com.RowingMachineMTV.web.index.vo.UserInfoVO;

public interface UserService {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(UserInfoVO userVO);

	int updateUserInfo(UserInfoVO param);

}
