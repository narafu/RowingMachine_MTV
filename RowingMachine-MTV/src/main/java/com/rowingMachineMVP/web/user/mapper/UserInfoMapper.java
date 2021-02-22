package com.rowingMachineMVP.web.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.web.quiz.vo.UserInfoVO;

@Mapper
public interface UserInfoMapper {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(String userId);

}
