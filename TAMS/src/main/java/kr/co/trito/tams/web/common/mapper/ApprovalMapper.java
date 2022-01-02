package kr.co.trito.tams.web.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AppvDto;
import kr.co.trito.tams.web.common.dto.AppvLineDto;
import kr.co.trito.tams.web.common.dto.ReqAppvDto;

@Mapper
public interface ApprovalMapper {
	
	public List<AppvDto> selectAppvList(SearchCondition condition);
	
	public AppvDto selectAppvInfo(Map<String,String> map);
	
	public List<AppvLineDto> selectAppvLine(Map<String,String> map);
	
	public int insertAppv(Map<String, Object> items);
	
	public int insertAppvLine(List<AppvLineDto> map);
	
	public int insertReqAppv(Map<String, Object> items);
	
	public int updateAppv(Map<String, Object> items);
	
	public int updateAppvId(Map<String, Object> items);
	
	public int deleteAppvLine(String appvId);
	
	public String maxAppvId(Map<String, Object> items);
}
