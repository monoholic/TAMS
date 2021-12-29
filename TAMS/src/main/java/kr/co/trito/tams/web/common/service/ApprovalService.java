package kr.co.trito.tams.web.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AppvDto;
import kr.co.trito.tams.web.common.dto.AppvLineDto;
import kr.co.trito.tams.web.common.dto.ReqAppvDto;
import kr.co.trito.tams.web.common.mapper.ApprovalMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class ApprovalService {

	@Autowired
	ApprovalMapper mapper;

	public void AppvLineInfo(Map<String,Object> map) {
		
		List<AppvLineDto> appvLineDto = new ArrayList<>();
		
		// 결재 순서
		int appvOrd = 1;
		
		List<String> appr  = (List<String>)map.get("appr");
		List<String> agree = (List<String>)map.get("agree");
		List<String> noti  = (List<String>)map.get("noti");
		
		for(String ap : appr) {
			AppvLineDto apDto = new AppvLineDto();
			apDto.setAppvId(map.get("appvId").toString());
			apDto.setAppvr(ap);
			apDto.setAppvDiv("APPROVAL");
			apDto.setAppvStus("ING");
			if("TMP".equals(map.get("appvStus").toString())){
				appvOrd = 0;
			}
			apDto.setAppvOrd(String.valueOf(appvOrd));
			apDto.setRegr(map.get("appvUserId").toString());
			
			appvLineDto.add(apDto);
			appvOrd++;
		}
		
		for(String ag : agree) {
			AppvLineDto apDto = new AppvLineDto();
			apDto.setAppvId(map.get("appvId").toString());
			apDto.setAppvr(ag);
			apDto.setAppvDiv("AGREEMENT");
			apDto.setAppvStus("ING");
			if("TMP".equals(map.get("appvStus").toString())){
				appvOrd = 0;
			}
			apDto.setAppvOrd(String.valueOf(appvOrd));
			apDto.setRegr(map.get("appvUserId").toString());
			
			appvLineDto.add(apDto);
			appvOrd++;
		}
		
		for(String no : noti) {
			AppvLineDto noDto = new AppvLineDto();
			noDto.setAppvId(map.get("appvId").toString());
			noDto.setAppvr(no);
			noDto.setAppvDiv("NOTIFY");
			noDto.setAppvStus("ING");
			if("TMP".equals(map.get("appvStus").toString())){
				appvOrd = 0;
			}
			noDto.setAppvOrd(String.valueOf(appvOrd));
			noDto.setRegr(map.get("appvUserId").toString());
			
			appvLineDto.add(noDto);
			appvOrd++;
		}
		
		if(!StringUtils.isEmpty(map.get("appvId").toString())) 
			deleteAppvLine(map.get("appvId").toString());
		
		insertAppvLine(appvLineDto);
	}
	
	public List<AppvDto> selectAppvList(SearchCondition condition) {
		return mapper.selectAppvList(condition);
	}
	
	public AppvDto selectAppvInfo(Map<String,String> map) {
		return mapper.selectAppvInfo(map);
	}
	
	public List<AppvLineDto> selectAppvLine(Map<String,String> map) {
		return mapper.selectAppvLine(map);
	}
	
	public int insertAppv(Map<String, Object> items) {
		return mapper.insertAppv(items);
	}
	
	public int insertAppvLine(List<AppvLineDto> map) {
		return mapper.insertAppvLine(map);
	}
	
	public int insertReqAppv(Map<String, Object> items) {
		return mapper.insertReqAppv(items);
	}
	
	public int updateAppv(Map<String, Object> items) {
		return mapper.updateAppv(items);
	}
	
	public int updateAppvId(Map<String, Object> items) {
		return mapper.updateAppvId(items);
	}
	
	public int deleteAppvLine(String appvId) {
		return mapper.deleteAppvLine(appvId);
	}
	
	public String reqNoCheck(Map<String, Object> items) {
		return mapper.reqNoCheck(items);
	}
	
	public String maxAppvId() {
		return mapper.maxAppvId();
	}
	
	public int reqNoCheckCnt() {
		return mapper.reqNoCheckCnt();
	}
}