package com.rowingMachineMVP.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseUtilServiceImpl implements BaseUtilService {
	
	@Autowired
	private BaseUtilMapper baseUtilMapper;

	@Override
	public List<Map<String, String>> selectCnmmCdList(String grpCd) {
		return baseUtilMapper.selectCnmmCdList(grpCd);
	}

}
