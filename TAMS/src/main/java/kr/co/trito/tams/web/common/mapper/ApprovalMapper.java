package kr.co.trito.tams.web.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AppvDto;
import kr.co.trito.tams.web.common.dto.AppvLineDto;

@Mapper
public interface ApprovalMapper {
	
	public List<AppvDto> selectAppvList(SearchCondition condition);
	
	public AppvDto selectAppvInfo(Map<String,String> map);
	
	public List<AppvLineDto> selectAppvLine(Map<String,String> map);
	
	public int insertAppv(AppvDto dto);
	
	public int updateAppv(AppvDto dto);
	
	public int insertAppvLine(List<AppvLineDto> dtoList);
	
	public int deleteAppvLine(String appvId);
	
}
