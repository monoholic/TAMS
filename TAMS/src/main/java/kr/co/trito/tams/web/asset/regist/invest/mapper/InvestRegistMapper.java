package kr.co.trito.tams.web.asset.regist.invest.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;

@Mapper
public interface InvestRegistMapper {
	
	public List<InvestRegistDto> selectInvestRegList(SearchCondition condition);
	
	public List<InvestRegistDto> selectInvestReg(SearchCondition condition);
	
	public List<AsetListDto> selectAsetList(SearchCondition condition);
	
	public List<InvestExcelDto> selectInvInqrExcelList(SearchCondition condition);	
	
	public List<ComCodeDto> selectAsetTypeInfo(String asetType);
	
	public int selectCountInvest(SearchCondition condition);
	
	public int selectCountAsetList(SearchCondition condition);
	
	public int savePoAset(Map<String, Object> param);
	
	public int saveAsetMas(Map<String, Object> param);
	
	public int saveAsetReq(Map<String, Object> param);
	
	public int saveAsetDtl(Map<String, Object> param);
	
	public int saveAsetDtlReq(Map<String, Object> param);
	
	public int selectMaxAsetNo();
	
	public int deletePoInfo(InvestDto invs);
}
