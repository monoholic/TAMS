package kr.co.trito.tams.web.asset.unused.reuse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;

@Mapper
public interface UnusedReuseMapper {
	
	public int selectCountUnusedReuseList(SearchCondition condition);
	
	public List<ReqMasDto> selectUnusedReuseList(SearchCondition condition);
	
	
	
	public int unusedRegistListInsert(Map<String, Object> data);
	
	public int unusedRegistListDelete(ReqMasDto dto);
	
	public List<ReqMasDto> selectUnusedRegistRegist(SearchCondition condition);
	
	public int selectCountAssetList(SearchCondition condition);
	
	public List<AsetReqDto> selectAssetList(SearchCondition condition);
	
	public int unusedRegistUpdate1(Map<String, Object> data);
	
	public int unusedRegistUpdate2(Map<String, Object> data);

	public int unusedRegistDelete1(Map<String, Object> data);
	
	public int unusedRegistDelete2(AsetReqDto dto);
}
