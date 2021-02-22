package com.rowingMachineMVP.web.quiz.service.impl;

import java.util.UUID;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rowingMachineMVP.web.quiz.service.UserService;
import com.rowingMachineMVP.web.quiz.vo.UserInfoVO;
import com.rowingMachineMVP.web.user.mapper.UserInfoMapper;

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
