package kr.co.trito.tams.web.asset.change.move.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.move.mapper.AssetMoveMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssetMoveService {
	
	@Autowired
	AssetMoveMapper mapper;
	
	public int selectCountAssetMovetList(SearchCondition condition) {
		return mapper.selectCountAssetMovetList(condition);
	}
	
	public List<ReqMasDto> selectAssetMoveList(SearchCondition condition){
		return mapper.selectAssetMoveList(condition);
	}
	
	public int assetMoveListInsert(Map<String, Object> data) {
		return mapper.assetMoveListInsert(data);
	}
	
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
