package kr.co.trito.tams.web.asset.unused.regist.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistAsetReqDto;
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistReqMasDto;
import kr.co.trito.tams.web.asset.unused.regist.mapper.UnusedRegistMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UnusedRegistService {
	
	@Autowired
	UnusedRegistMapper mapper;
	
	public int selectCountUnusedRegistList(SearchCondition condition) {
		return mapper.selectCountUnusedRegistList(condition);
	}
	
	public List<RegistReqMasDto> selectUnusedRegistList(SearchCondition condition){
		return mapper.selectUnusedRegistList(condition);
	}
	
	public int unusedRegistListInsert(Map<String, Object> data) {
		return mapper.unusedRegistListInsert(data);
	}
	
	public int unusedRegistListDelete(String reqno) {
		return mapper.unusedRegistListDelete(reqno);
	}

	public int unusedRegistListAsetDelete(String reqno) {
		return mapper.unusedRegistListAsetDelete(reqno);
	}
	
	public List<RegistReqMasDto> selectUnusedRegistRegist(SearchCondition condition) {
		return mapper.selectUnusedRegistRegist(condition);
	}
	
	public int selectCountAssetList(SearchCondition condition) {
		return mapper.selectCountAssetList(condition);
	}
	
	public List<RegistAsetReqDto> selectAssetList(SearchCondition condition) {
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
	
	public int unusedRegistDelete2(RegistAsetReqDto dto) {
		return mapper.unusedRegistDelete2(dto);
	}
	
	public int unusedRegistDelete3(Map<String, Object> data) {
		return mapper.unusedRegistDelete3(data);
	}
}
