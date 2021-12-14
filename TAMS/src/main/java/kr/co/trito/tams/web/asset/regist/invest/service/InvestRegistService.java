package kr.co.trito.tams.web.asset.regist.invest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.asset.regist.invest.mapper.InvestRegistMapper;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestRegistService {
	
	@Autowired
	InvestRegistMapper mapper;
	
	/** 
	 * 투자자산등록 다건 조회
	 * */
	public List<InvestRegistDto> selectInvestRegList(SearchCondition condition) {
		return mapper.selectInvestRegList(condition);
	}
	
	/** 
	 * 투자자산등록 단건 조회
	 * */
	public List<InvestRegistDto> selectInvestReg(SearchCondition condition) {
		return mapper.selectInvestReg(condition);
	}
	
	/** 
	 * 등록자산목록 다건 조회
	 * */
	public List<AsetListDto> selectAsetList(SearchCondition condition) {
		return mapper.selectAsetList(condition);
	}
	
	/** 
	 * 투자자산등록 엑셀 리스트 다운로드
	 * */
	public List<InvestExcelDto> selectInvInqrExcelList(SearchCondition condition) {
		return mapper.selectInvInqrExcelList(condition);
	}
	
	/** 
	 * 투자자산등록 리스트 카운트
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
