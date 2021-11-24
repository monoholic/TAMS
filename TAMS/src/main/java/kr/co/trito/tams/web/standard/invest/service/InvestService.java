package kr.co.trito.tams.web.standard.invest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import kr.co.trito.tams.web.standard.invest.mapper.InvestMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestService {
	
	@Autowired
	InvestMapper mapper;
	
	public List<InvestDto> selectInvestMngList(SearchCondition condition) {
		return mapper.selectInvestMngList(condition);
	}
	
	public List<CodeDto> selectCodeGrpList(String email){
		return mapper.selectCodeGrpList(email);
	}
	
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
	public int savePoInfo(List<InvestDto> invs){
		return mapper.savePoInfo(invs);
	}
	
	public int codeMngInsert(CodeDto dto) {
		return mapper.codeMngInsert(dto);
	}
	
	public int codeMngUpdate(CodeDto dto) {
		return mapper.codeMngUpdate(dto);
	}
	
	public int codeMngDelete(CodeDto dto) {
		return mapper.codeMngDelete(dto);
	}
}
