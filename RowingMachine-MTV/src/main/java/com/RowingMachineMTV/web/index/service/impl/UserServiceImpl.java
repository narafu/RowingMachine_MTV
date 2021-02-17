package com.RowingMachineMTV.web.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RowingMachineMTV.web.index.mapper.UserInfoMapper;
import com.RowingMachineMTV.web.index.service.UserService;
import com.RowingMachineMTV.web.index.vo.UserInfoVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public int insertUserInfo(UserInfoVO userVO) {
		return userInfoMapper.insertUserInfo(userVO);
	}

	@Override
	public UserInfoVO getUserInfo(UserInfoVO userVO) {
		return userInfoMapper.getUserInfo(userVO);
	}

	@Override
	public int updateUserInfo(UserInfoVO userVO) {
		return userInfoMapper.updateUserInfo(userVO);
	}

}
