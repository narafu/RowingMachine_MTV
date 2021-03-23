package com.rowingMachineMVP.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUtilMapper {

	List<Map<String, String>> selectCnmmCdList(String grpCd);

}
