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
	
	
	public String regAppvInfo(Map<String,Object> map, String status) {
		
		AppvDto appv = (AppvDto)map.get("appvDto");
		
		int insCnt = insertAppv(appv);
		
		List<String> appr  = (List<String>)map.get("appr");
		List<String> agree = (List<String>)map.get("agree");
		List<String> noti  = (List<String>)map.get("noti");
		
		//상신자 정보 세팅
		int apprOrd  = 1;
		
		String appvId = appv.getAppvId();
		
		List<AppvLineDto> appvLineDto = new ArrayList<>();
		
		AppvLineDto appvUserDto = new AppvLineDto();
		appvUserDto.setAppvId(appv.getAppvId());
		appvUserDto.setAppvr(appv.getAppvUserId());
		appvUserDto.setAppvDiv("SUBMIT");
		if( "F".equals(status)) appvUserDto.setAppvStus("CMP");
		else                    appvUserDto.setAppvStus("ING");
		appvUserDto.setAppvOrd(String.valueOf(apprOrd));
		appvUserDto.setRegr(appv.getAppvUserId());
		
		appvLineDto.add(appvUserDto);
		
		for(String ap : appr) {
			log.error("appr >> "+ap);
			apprOrd++;
			AppvLineDto apDto = new AppvLineDto();
			apDto.setAppvId(appvId);
			apDto.setAppvr(ap);
			apDto.setAppvDiv("APPROVAL");
			apDto.setAppvStus("ING");
			apDto.setAppvOrd(String.valueOf(apprOrd));
			apDto.setRegr(appv.getAppvUserId());
			
			appvLineDto.add(apDto);
		}
		
		for(String ag : agree) {
			log.error("agree >> "+ag);
			apprOrd++;
			AppvLineDto agDto = new AppvLineDto();
			agDto.setAppvId(appvId);
			agDto.setAppvr(ag);
			agDto.setAppvDiv("AGREEMENT");
			agDto.setAppvStus("ING");
			agDto.setAppvOrd(String.valueOf(apprOrd));
			agDto.setRegr(appv.getAppvUserId());
			
			appvLineDto.add(agDto);
		}
		
		for(String no : noti) {
			log.error("noti >> "+no);
			apprOrd++;
			AppvLineDto noDto = new AppvLineDto();
			noDto.setAppvId(appvId);
			noDto.setAppvr(no);
			noDto.setAppvDiv("NOTIFY");
			noDto.setAppvStus("ING");
			noDto.setAppvOrd(String.valueOf(apprOrd));
			noDto.setRegr(appv.getAppvUserId());
			
			appvLineDto.add(noDto);
		}
		
		if( !StringUtils.isEmpty(appv.getAppvId()) ) deleteAppvLine(appv.getAppvId());
		
		int lineCnt = insertAppvLine(appvLineDto);
		
		String rst = "202";
		if( insCnt > 0 && lineCnt > 0 ) {
			rst = "200";
		}
		
		return rst;
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
	
	public int insertAppv(AppvDto dto) {
		return mapper.insertAppv(dto);
	}
	
	public int updateAppv(AppvDto dto) {
		return mapper.updateAppv(dto);
	}
	
	public int insertAppvLine(List<AppvLineDto> dtoList) {
		return mapper.insertAppvLine(dtoList);
	}
	
	public int deleteAppvLine(String appvId) {
		return mapper.deleteAppvLine(appvId);
	}
	
}