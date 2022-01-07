package kr.co.trito.tams.web.asset.unused.regist.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;

@Mapper
public interface UnusedRegistMapper {
	
	public int selectCountUnusedRegistList(SearchCondition condition);
	
	public List<ReqMasDto> selectUnusedRegistList(SearchCondition condition);
	
	public int unusedRegistListInsert(Map<String, Object> data);
	
	
	/* 위까지 */
	
	
	
	public int assetMoveListDelete(ReqMasDto dto);
	
	public List<ReqMasDto> selectAssetMoveRegist(SearchCondition condition);
	
	public List<AsetReqDto> selectAssetList(SearchCondition condition);
	
	public int selectCountAssetList(SearchCondition condition);
	
	public int assetMoveUpdate1(Map<String, Object> data);
	
	public int assetMoveUpdate2(Map<String, Object> data);
	
	public int assetMoveDelete1(Map<String, Object> data);
	
	public int assetMoveDelete2(AsetReqDto dto);
}
