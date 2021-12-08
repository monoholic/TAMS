package kr.co.trito.tams.web.aset.regist.regaset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.aset.regist.invest.dto.InvExcelDto;
import kr.co.trito.tams.web.aset.regist.invest.dto.InvInqrDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;

@Mapper
public interface RegAsetMapper {
	
	public List<InvInqrDto> selectInvestInqrList(SearchCondition condition);
	
	public List<InvExcelDto> selectInvInqrExcelList(SearchCondition condition);	
	
	public int selectCountInvest(SearchCondition condition);
	
	public int saveInvestInfo(InvestDto invs);
	
	public int savePoInfo(InvestDto invs);
	
	public int deletePoInfo(InvestDto invs);
}
