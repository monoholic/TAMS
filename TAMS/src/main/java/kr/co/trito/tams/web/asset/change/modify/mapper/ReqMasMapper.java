package kr.co.trito.tams.web.asset.change.modify.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;

@Mapper
public interface ReqMasMapper {
	
	public int selectCountRequestList(SearchCondition condtion);
	
	public List<ReqMasDto> selectRequestList(SearchCondition condition);
	
	public List<ReqMasExcelDto> selectReqExcelList(SearchCondition condition);
	
	public int requestListInsert(ReqMasDto dto);
	
	public int requestListDelete(ReqMasDto dto);
	
	public List<CodeDto> selectReqStusList(String email);

	public List<ReqMasDto> selectRequestRegist(SearchCondition condition);
	
	public List<AsetMasDto> selectAsetReqList(SearchCondition condition);
}
