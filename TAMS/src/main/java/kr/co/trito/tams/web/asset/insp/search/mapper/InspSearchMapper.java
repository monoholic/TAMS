package kr.co.trito.tams.web.asset.insp.search.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.insp.manage.dto.InspMasterDto;
import kr.co.trito.tams.web.asset.insp.search.dto.InspSearchMasterDto;

@Mapper
public interface InspSearchMapper {

	List<InspSearchMasterDto> selectInspNmList(Map<String, Object> items);

	List<InspSearchMasterDto> selectInspDeptNmList(Map<String, Object> items);

	int selectCountInspSearchList(SearchCondition condition);

	List<InspSearchMasterDto> selectInspSearchList(SearchCondition condition);

	List<InspSearchMasterDto> selectInspMasterSearchList(Map<String, Object> items);

	int updateInspAsetList(Map<String, Object> items);

	List<InspSearchMasterDto> selectRecentInspYear(Map<String, Object> items);

	
	

}
