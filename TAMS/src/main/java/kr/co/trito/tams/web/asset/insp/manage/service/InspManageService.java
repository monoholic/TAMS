package kr.co.trito.tams.web.asset.insp.manage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.insp.manage.controller.InspManageController;
import kr.co.trito.tams.web.asset.insp.manage.dto.InspMasterDto;
import kr.co.trito.tams.web.asset.insp.manage.mapper.InspManageMapper;
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistReqMasDto;
import kr.co.trito.tams.web.asset.unused.regist.mapper.UnusedRegistMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InspManageService {

	
	@Autowired
	InspManageMapper mapper;
	
	
	public int selectCountInspManageList(SearchCondition condition) {
		log.info("[service][selectCountInspManageList]");
		return mapper.selectCountInspManageList(condition);
	}
	
	public List<InspMasterDto> selectInspManageList(SearchCondition condition){
		log.info("[service][selectInspManageList]");
		return mapper.selectInspManageList(condition);
	}

	public List<InspMasterDto> selectInspNmList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspNmList]");
		return mapper.selectInspNmList(condition);
	}

	public int deleteInspList(InspMasterDto dto) {
		// TODO Auto-generated method stub
		log.info("[service][deleteInspList]");
		return mapper.deleteInspList(dto);
	}

	public int insertInspMaster(Map<String, Object> params) {
		// TODO Auto-generated method stub
		log.info("[service][insertInspMaster]");
		return mapper.insertInspMaster(params);
		
	}

	public List<InspMasterDto> selectInspAsetList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspAsetList]");
		return mapper.selectInspAsetList(condition);
	}

	public int selectCountInspAsetList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectCountInspAsetList]");
		return mapper.selectCountInspAsetList(condition);
	}
	
}
