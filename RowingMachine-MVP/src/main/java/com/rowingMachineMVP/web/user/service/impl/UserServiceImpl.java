package com.rowingMachineMVP.web.user.service.impl;

import java.util.UUID;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rowingMachineMVP.web.user.mapper.UserInfoMapper;
import com.rowingMachineMVP.web.user.service.UserService;
import com.rowingMachineMVP.web.user.vo.UserInfoVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public int insertUserInfo(UserInfoVO param) {
		String userId = param.getUserId();
		if (StringUtils.isEmpty(userId)) {
			String uuid = UUID.randomUUID().toString();
			param.setUserId(uuid);
			param.setUserNm("GUEST");
		} else {
			String[] userNm = userId.split("@");
			param.setUserNm(userNm[0]);
		}
		return userInfoMapper.insertUserInfo(param);
	}

	@Override
	public UserInfoVO getUserInfo(String userId) {
		return userInfoMapper.getUserInfo(userId);
	}

}
