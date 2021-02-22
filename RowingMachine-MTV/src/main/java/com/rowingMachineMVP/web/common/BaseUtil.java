package com.rowingMachineMVP.web.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseUtil {

	@Autowired
	private BaseUtilService baseUtilService;

	public List<Map<String, String>> getCnmmCdList(String grpCd) {
		List<Map<String, String>> cmmnCdMapList = baseUtilService.selectCnmmCdList(grpCd);
		return cmmnCdMapList;
	}

}
