package kr.co.trito.tams.web.standard.invest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import kr.co.trito.tams.web.standard.invest.mapper.InvestMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestService {
	
	@Autowired
	InvestMapper mapper;
	
	/** 
	 * 투자정보관리 리스트 조회
	 * */
	public List<InvestDto> selectInvestMngList(SearchCondition condition) {
		return mapper.selectInvestMngList(condition);
	}
	
	/** 
	 * 투자정보관리 리스트 카운트
	 * */
	public int selectCountInvest(SearchCondition condition) {
		return mapper.selectCountInvest(condition);
	}
	
	/** 
	 * TB_INV_MAS 정보 등록
	 * */
	public int saveInvestInfo(InvestDto invs){
		return mapper.saveInvestInfo(invs);
	}
	
	/** 
	 * TB_PO 정보 등록
	 * */
	public int savePoInfo(InvestDto invs){
		return mapper.savePoInfo(invs);
	}
	
	/** 
	 * TB_PO 정보 등록
	 * */
	public int deletePoInfo(InvestDto invs){
		return mapper.deletePoInfo(invs);
	}
}
