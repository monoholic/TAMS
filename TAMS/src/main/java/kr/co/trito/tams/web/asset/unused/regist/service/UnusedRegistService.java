package kr.co.trito.tams.web.asset.unused.regist.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
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
	
	public List<ReqMasDto> selectUnusedRegistList(SearchCondition condition){
		return mapper.selectUnusedRegistList(condition);
	}
	
	public int unusedRegistListInsert(Map<String, Object> data) {
		return mapper.unusedRegistListInsert(data);
	}
	
	/* 위까지 */
	

	
	public int assetMoveListDelete(ReqMasDto dto) {
		return mapper.assetMoveListDelete(dto);
	}
	
	public List<ReqMasDto> selectAssetMoveRegist(SearchCondition condition) {
		return mapper.selectAssetMoveRegist(condition);
	}
	
	public List<AsetReqDto> selectAssetList(SearchCondition condition) {
		return mapper.selectAssetList(condition);
	}
	
	public int selectCountAssetList(SearchCondition condition) {
		return mapper.selectCountAssetList(condition);
	}
	
	public int assetMoveUpdate1(Map<String, Object> data) {
		return mapper.assetMoveUpdate1(data);
	};
	
	public int assetMoveUpdate2(Map<String, Object> data) {
		return mapper.assetMoveUpdate2(data);
	};
	
	public int assetMoveDelete1(Map<String, Object> data) {
		return mapper.assetMoveDelete1(data);
	}
	
	public int assetMoveDelete2(AsetReqDto dto) {
		return mapper.assetMoveDelete2(dto);
	}
}
