package kr.co.trito.tams.web.asset.unused.reuse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.unused.reuse.mapper.UnusedReuseMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UnusedReuseService {
	
	@Autowired
	UnusedReuseMapper mapper;
	
	public int selectCountUnusedReuseList(SearchCondition condition) {
		return mapper.selectCountUnusedReuseList(condition);
	}
	
	public List<ReqMasDto> selectUnusedReuseList(SearchCondition condition){
		return mapper.selectUnusedReuseList(condition);
	}
	
	
	
	public int unusedRegistListInsert(Map<String, Object> data) {
		return mapper.unusedRegistListInsert(data);
	}
	
	public int unusedRegistListDelete(ReqMasDto dto) {
		return mapper.unusedRegistListDelete(dto);
	}

	public List<ReqMasDto> selectUnusedRegistRegist(SearchCondition condition) {
		return mapper.selectUnusedRegistRegist(condition);
	}
	
	public int selectCountAssetList(SearchCondition condition) {
		return mapper.selectCountAssetList(condition);
	}
	
	public List<AsetReqDto> selectAssetList(SearchCondition condition) {
		return mapper.selectAssetList(condition);
	}
	
	public int unusedRegistUpdate1(Map<String, Object> data) {
		return mapper.unusedRegistUpdate1(data);
	}
	
	public int unusedRegistUpdate2(Map<String, Object> data) {
		return mapper.unusedRegistUpdate2(data);
	}	
	
	public int unusedRegistDelete1(Map<String, Object> data) {
		return mapper.unusedRegistDelete1(data);
	}
	
	public int unusedRegistDelete2(AsetReqDto dto) {
		return mapper.unusedRegistDelete2(dto);
	}
}
