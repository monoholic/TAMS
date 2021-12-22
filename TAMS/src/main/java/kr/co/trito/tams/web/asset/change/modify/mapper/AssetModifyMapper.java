package kr.co.trito.tams.web.asset.change.modify.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.AsetReqDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.codegrp.dto.CodegrpDto;

@Mapper
public interface AssetModifyMapper {
	
	public int selectCountRequestList(SearchCondition condtion);
	
	public List<ReqMasDto> selectRequestList(SearchCondition condition);
	
	public List<ReqMasExcelDto> selectReqExcelList(SearchCondition condition);
	
	public int requestListInsert(Map<String, Object> data);
	
	public int requestListDelete(ReqMasDto dto);
	
	public List<ReqMasDto> selectRequestRegist(SearchCondition condition);
	
	public List<AsetReqDto> selectAsetReqList(SearchCondition condition);
	
	public int selectCountAsetReqList(SearchCondition condition);
	
	
	public int requestRegUpdate1(Map<String, Object> data);
	
	public int requestRegUpdate2(Map<String, Object> data);
	
	public int requestRegDelete(Map<String, Object> items);
	
	public int requestRegDelete2(AsetReqDto dto);
}
