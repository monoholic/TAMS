package kr.co.trito.tams.web.asset.insp.manage.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.insp.manage.dto.InspMasterDto;

@Mapper
public interface InspManageMapper {

	
	
	public int selectCountInspManageList(SearchCondition condition);
	
	public List<InspMasterDto> selectInspManageList(SearchCondition condition);

	public List<InspMasterDto> selectInspNmList(SearchCondition condition);

	public int deleteInspMaster(Map<String, Object> param);

	public int insertInspMaster(Map<String, Object> params);

	public int updateInspMaster(Map<String, Object> param);
	
	public List<InspMasterDto> selectInspAsetList(SearchCondition condition);

	public int selectCountInspAsetList(SearchCondition condition);

	public List<InspMasterDto> selectInspAsetAddList(SearchCondition condition);

	public int selectInspAsetAddCoutList(SearchCondition condition);

	public int insertInspAssetList(InspMasterDto dto);

	public int updateInspAsetList(Map<String, Object> param);

	public int deleteInspAsetList(Map<String, Object> param);

	public int selectCountInspMngList(SearchCondition condition);

	public List<InspMasterDto> selectInspMngList(SearchCondition condition);

	public int updateInspMngList(Map<String, Object> param);

}
