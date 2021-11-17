package kr.co.trito.tams.web.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;

@Mapper
public interface CommonMapper {
	
	public List<DeptDto> selectDeptPopupList(SearchCondition condtion);
	
	public List<UserMngDto> selectUserPopupList(SearchCondition condtion);
	
	public List<FileDto> selectFileList(SearchCondition condtion);
	
	public int saveFileInfo(FileDto dto);
	
	public int updateDwldCnt(FileDto dto);
	
}
