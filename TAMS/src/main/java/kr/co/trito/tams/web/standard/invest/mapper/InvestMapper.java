package kr.co.trito.tams.web.standard.invest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;

@Mapper
public interface InvestMapper {
	
	public List<InvestDto> selectInvestMngList(SearchCondition condition);
	
	public List<CodeDto> selectCodeGrpList(String email);
	
	public int selectCountInvest(SearchCondition condition);
	
	public int saveInvestInfo(InvestDto invs);
	
	public int savePoInfo(List<InvestDto> invs);
	
	public int codeMngInsert(CodeDto dto);
	
	public int codeMngUpdate(CodeDto dto);
	
	public int codeMngDelete(CodeDto dto);
}
