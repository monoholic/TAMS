package kr.co.trito.tams.web.asset.regist.invest.mapper;

import java.util.List;

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
	
	public List<ComCodeDto> selectAsetTypeInfo(SearchCondition condition);
	
	public int selectCountInvest(SearchCondition condition);
	
	public int selectCountAsetList(SearchCondition condition);
	
	public int saveInvestInfo(InvestDto invs);
	
	public int savePoInfo(InvestDto invs);
	
	public int deletePoInfo(InvestDto invs);
}
