package kr.co.trito.tams.web.asset.insp.manage.mapper;

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

	public int deleteInspList(InspMasterDto dto);

	public int insertInspMaster(Map<String, Object> params);

	public List<InspMasterDto> selectInspAsetList(SearchCondition condition);

	public int selectCountInspAsetList(SearchCondition condition);
}
