package kr.co.trito.tams.web.asset.insp.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.insp.search.dto.InspSearchMasterDto;
import kr.co.trito.tams.web.asset.insp.search.mapper.InspSearchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InspSearchService {

	
	@Autowired
	InspSearchMapper mapper;

	public List<InspSearchMasterDto> selectInspNmList(Map<String, Object> items) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspNmList]");
		return mapper.selectInspNmList(items);
	}

	public List<InspSearchMasterDto> selectInspDeptNmList(Map<String, Object> items) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspDeptNmList]");
		return mapper.selectInspDeptNmList(items);
	}

	public int selectCountInspSearchList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectCountInspSearchList]");
		return mapper.selectCountInspSearchList(condition);
	}

	public List<InspSearchMasterDto> selectInspSearchList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectInspSearchList]");
		return mapper.selectInspSearchList(condition);
	}
	
	
	
}
