package kr.co.trito.tams.web.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.web.common.dto.AppvDto;
import kr.co.trito.tams.web.common.dto.AppvLineDto;

@Mapper
public interface ApprovalMapper {
	
	public int insertAppv(AppvDto dto);
	
	public int updateAppv(AppvDto dto);
	
	public int insertAppvLine(List<AppvLineDto> dtoList);
	
	public int deleteAppvLine(AppvLineDto dto);
	
}
