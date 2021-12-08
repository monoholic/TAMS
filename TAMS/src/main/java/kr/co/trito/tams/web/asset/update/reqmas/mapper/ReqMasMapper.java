package kr.co.trito.tams.web.asset.update.reqmas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.update.reqmas.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.update.reqmas.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;

@Mapper
public interface ReqMasMapper {
	
	public int selectCountReqInqr(SearchCondition condtion);
	
	public List<ReqMasDto> selectReqInqrList(SearchCondition condition);
	
	public List<ReqMasExcelDto> selectReqInqrExcelList(SearchCondition condition);
	
	public int reqInqrInsert(ReqMasDto dto);
	
	public int reqInqrUpdate(ReqMasDto dto);
	
	public int reqInqrDelete(ReqMasDto dto);
	
	public List<CodeDto> selectReqTypeList(String email);
	
	public List<CodeDto> selectReqStusList(String email);

	
}
