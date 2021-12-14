package kr.co.trito.tams.web.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.file.excel.InvDto;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.common.dto.CodeTreeDto;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.common.dto.ComCodeParamDto;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.dto.MenuRoleCheckDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;

@Mapper
public interface CommonMapper {
	
	public List<DeptDto> selectDeptPopupList(SearchCondition condtion);
	
	public List<CodeTreeDto> selectDeptTree(String searchType);
	
	public List<UserMngDto> selectUserPopupList(SearchCondition condtion);
	
	public List<UserMngDto> selectUserFilterPopupList(SearchCondition condtion);
	
	public List<ComCodeDto> commSelectBox(ComCodeParamDto params);
	
	public List<FileDto> selectFileList(SearchCondition condtion);
	
	public int saveFileInfo(FileDto dto);
	
	public int deleteFile(FileDto dto);
	
	public int updateDwldCnt(FileDto dto);
	
	public MenuRoleCheckDto selectMenuRoleCheck(MenuRoleCheckDto dto);
	
	public Map<String,String> selectUserDeptInfo(Map<String,String> map);
	
	public int saveInvestInfo(InvDto invs);
	
	public int savePoInfo(List<InvDto> invs);
	
	public List<CodeTreeDto> selectCodeTree(Map<String,String> map);
	
	public List<Map<String,String>> selectChart1();
	public List<Map<String,String>> selectChart2();
	
	public List<AsetMasDto> selectAsetMasList(SearchCondition condition);
	
	public int selectCountAsetMas(SearchCondition condition);
	
	public List<CodeDto> selectAsetType(String email);
}
