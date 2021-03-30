package com.rowingMachineMVP.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rowingMachineMVP.user.vo.UserVO;

@Mapper
public interface UserInfoMapper {

	int insertUserInfo(UserVO userVO);

	UserVO getUserInfo(String userId);

}
