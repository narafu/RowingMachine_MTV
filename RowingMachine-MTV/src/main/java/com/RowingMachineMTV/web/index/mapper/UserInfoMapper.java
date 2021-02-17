package com.RowingMachineMTV.web.index.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.RowingMachineMTV.web.index.vo.UserInfoVO;

@Mapper
public interface UserInfoMapper {

	int insertUserInfo(UserInfoVO userVO);

	UserInfoVO getUserInfo(UserInfoVO userVO);

	int updateUserInfo(UserInfoVO userVO);

}
