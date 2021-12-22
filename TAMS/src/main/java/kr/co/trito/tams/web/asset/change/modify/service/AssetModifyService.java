package kr.co.trito.tams.web.asset.change.modify.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.asset.change.modify.mapper.AssetModifyMapper;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssetModifyService {
	
	@Autowired
	AssetModifyMapper mapper;
	
	public int selectCountRequestList(SearchCondition condition) {
		return mapper.selectCountRequestList(condition);
	}
	
	public List<ReqMasDto> selectRequestList(SearchCondition condition){
		return mapper.selectRequestList(condition);
	}
	
	public List<ReqMasExcelDto> selectReqExcelList(SearchCondition condition) {
		return mapper.selectReqExcelList(condition);
	}
	
	public int requestListInsert(Map<String, Object> data) {
		return mapper.requestListInsert(data);				
	}
	
	public int requestListDelete(ReqMasDto dto) {
		return mapper.requestListDelete(dto);
	}
	
	public List<ReqMasDto> selectRequestRegist(SearchCondition condition) {
		return mapper.selectRequestRegist(condition);
	}
	
	public List<AsetReqDto> selectAsetReqList(SearchCondition condition) {
		return mapper.selectAsetReqList(condition);
	}
	
	public int selectCountAsetReqList(SearchCondition condition) {
		return mapper.selectCountAsetReqList(condition);
	}
	
	public int requestRegUpdate1(Map<String, Object> data) {
		return mapper.requestRegUpdate1(data);
	};
	
	public int requestRegUpdate2(Map<String, Object> data) {
		return mapper.requestRegUpdate2(data);
	};
	
	public int requestRegDelete(Map<String, Object> items) {
		return mapper.requestRegDelete(items);
	}
	
	public int requestRegDelete2(AsetReqDto dto) {
		return mapper.requestRegDelete2(dto);
	}
}
