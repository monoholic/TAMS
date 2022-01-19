package kr.co.trito.tams.web.asset.unused.reuse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.unused.reuse.dto.ReuesReqMasDto;
import kr.co.trito.tams.web.asset.unused.reuse.dto.ReuseAsetReqDto;

@Mapper
public interface UnusedReuseMapper {
	
	public int selectCountUnusedReuseList(SearchCondition condition);
	
	public List<ReuesReqMasDto> selectUnusedReuseList(SearchCondition condition);
	
	public int unusedReuseListInsert(Map<String, Object> data);
	
	public int unusedReuseListDelete(String reqno);
	
	public int unusedReuseListAsetDelete(String reqno);
	
	public List<ReuesReqMasDto> selectUnusedReuseRegist(SearchCondition condition);
	
	public int selectCountAssetList(SearchCondition condition);
	
	public List<ReuseAsetReqDto> selectAssetList(SearchCondition condition);
	
	public int unusedReuseUpdate1(Map<String, Object> data);
	
	public int unusedReuseUpdate2(Map<String, Object> data);

	public int unusedReuseDelete1(Map<String, Object> data);
	
	public int unusedReuseDelete2(ReuseAsetReqDto dto);
	
	public int unusedReuseDelete3(Map<String, Object> data);
	
	public List<ReuseAsetReqDto> selectReuseUserPopupList(SearchCondition condtion);
}
