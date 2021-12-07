package kr.co.trito.tams.web.aset.regist.invest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.aset.regist.invest.dto.InvExcelDto;
import kr.co.trito.tams.web.aset.regist.invest.dto.InvInqrDto;
import kr.co.trito.tams.web.aset.regist.invest.mapper.InvInqrMapper;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvInqrService {
	
	@Autowired
	InvInqrMapper mapper;
	
	/** 
	 * 투자정보관리 리스트 조회
	 * */
	public List<InvInqrDto> selectInvestInqrList(SearchCondition condition) {
		return mapper.selectInvestInqrList(condition);
	}
	
	/** 
	 * 투자정보관리 엑셀 리스트 다운로드
	 * */
	public List<InvExcelDto> selectInvInqrExcelList(SearchCondition condition) {
		return mapper.selectInvInqrExcelList(condition);
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
