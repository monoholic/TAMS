package kr.co.trito.tams.web.asset.insp.manage.service;

import java.util.HashMap;
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

	public int deleteInspMaster(Map<String, Object> param) {
		// TODO Auto-generated method stub
		log.info("[service][deleteInspMaster]");
		return mapper.deleteInspMaster(param);
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

	public List<InspMasterDto> selectInspAsetAddList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspAsetAddList]");
		return mapper.selectInspAsetAddList(condition);
	}

	public int selectInspAsetAddCoutList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspAsetAddCoutList]");
		return mapper.selectInspAsetAddCoutList(condition);
	}

	public int insertInspAssetList(InspMasterDto dto) {
		// TODO Auto-generated method stub
		log.info("[service][insertInspAssetList]");
		return mapper.insertInspAssetList(dto);
	}

	public int updateInspAsetList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		log.info("[service][updateInspAsetList]");
		return mapper.updateInspAsetList(param);
	}

	public int deleteInspAsetList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		log.info("[service][deleteInspAsetList]");
		return mapper.deleteInspAsetList(param);
	}

	public int updateInspMaster(Map<String, Object> param) {
		// TODO Auto-generated method stub
		log.info("[service][updateInspMaster]");
		return mapper.updateInspMaster(param);
	}
	
}
