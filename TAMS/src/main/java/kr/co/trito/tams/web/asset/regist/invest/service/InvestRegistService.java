package kr.co.trito.tams.web.asset.regist.invest.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jdk.internal.org.jline.utils.Log;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.asset.regist.invest.mapper.InvestRegistMapper;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.standard.invest.dto.InvestDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
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
	 * 자산유형특성정보 조회
	 * */
	public List<ComCodeDto> selectAsetTypeInfo(SearchCondition condition) {
		return mapper.selectAsetTypeInfo(condition);
	}
	
	/** 
	 * 투자자산등록 리스트 카운트
	 * */
	public int selectCountInvest(SearchCondition condition) {
		return mapper.selectCountInvest(condition);
	}
	
	/** 
	 * 등록자산목록 리스트 카운트
	 * */
	public int selectCountAsetList(SearchCondition condition) {
		return mapper.selectCountAsetList(condition);
	}
	
	/** 
	 * 신규자산등록 : TB_PO_ASET 정보 등록
	 * */
	public int savePoAset(Map<String, Object> param){
		return mapper.savePoAset(param);
	}
	
	/** 
	 * 신규자산등록 : TB_ASET_MAS 정보 등록
	 * */
	public int saveAsetMas(Map<String, Object> param){
		return mapper.saveAsetMas(param);
	}
	
	/** 
	 * 신규자산등록 : TB_ASET_REQ 정보 등록
	 * */
	public int saveAsetReq(Map<String, Object> param){
		return mapper.saveAsetReq(param);
	}
	
	/** 
	 * 신규자산등록 : TB_ASET_DTL 정보 등록
	 * */
	public int saveAsetDtl(Map<String, Object> param){
		return mapper.saveAsetDtl(param);
	}
	
	/** 
	 * 신규자산등록 : TB_ASET_DTL_REQ 정보 등록
	 * */
	public int saveAsetDtlReq(Map<String, Object> param){
		return mapper.saveAsetDtlReq(param);
	}
	
	/** 
	 * 현재 ASET_NO의 마지막 시퀀스 조회 
	 * */
	public int selectMaxAsetNo(String poNo){
		return mapper.selectMaxAsetNo(poNo);
	}
	
	/**
	 * 신규자산등록 
	 */
	public String registAset(UserDetails userDetail, List<Map<String, Object>> params, String code) {
		
		int sequence = this.selectMaxAsetNo(params.get(0).get("poNo").toString());
		
		UserInfo userInfo = (UserInfo) userDetail;
		String userId = userInfo.getDto().getUserId();
		
		int cnt = 0;
		
		for (Map<String, Object> param : params) { 
			param.put("regr", userId);
			param.put("updr", userId);
			param.put("asetNo", sequence);
			
			if(this.savePoAset(param) > 0 && this.saveAsetMas(param) > 0 && this.saveAsetDtl(param) > 0)
				cnt++;
		}
		
		if (cnt > 0) code = "200";
		
		return code;
	}
	
	/** 
	 * TB_PO 정보 등록
	 * */
	public int deletePoInfo(InvestDto invs){
		return mapper.deletePoInfo(invs);
	}
}
