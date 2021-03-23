package com.rowingMachineMVP.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.user.vo.UserInfoVO;

@Mapper
public interface UserInfoMapper {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(String userId);

}
