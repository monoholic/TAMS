package kr.co.trito.tams.web.standard.invest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;

@Mapper
public interface InvestMapper {
	
	public List<InvestDto> selectInvestMngList(SearchCondition condition);
	
	public int selectCountInvest(SearchCondition condition);
	
	public int saveInvestInfo(InvestDto invs);
	
	public int savePoInfo(InvestDto invs);
	
	public int deletePoInfo(InvestDto invs);
}
