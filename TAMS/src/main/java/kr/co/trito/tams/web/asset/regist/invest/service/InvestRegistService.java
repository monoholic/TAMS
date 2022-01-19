package kr.co.trito.tams.web.asset.regist.invest.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.invest.dto.AsetListDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.AssetDtlDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.AssetMasDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.AssetUploadDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestExcelDto;
import kr.co.trito.tams.web.asset.regist.invest.dto.InvestRegistDto;
import kr.co.trito.tams.web.asset.regist.invest.mapper.InvestRegistMapper;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookBatchDto;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
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
	 * 자산유형특성정보 조회
	 * */
	public List<ComCodeDto> selectAsetTypeInfo(String asetType) {
		return mapper.selectAsetTypeInfo(asetType);
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
	 * 신규자산등록 
	 */
	public String registAset(String userId, List<Map<String, Object>> params) {
		
		int maxAssetNo = mapper.selectMaxAsetNo();
		
		for (Map<String, Object> param : params) {
			
			boolean isNew = StringUtils.isEmpty((String)param.get("asetNo"));				
			
			param.put("regr", userId);
			param.put("updr", userId);
			param.put("qty" , "1");
			
			if(isNew) 
				param.put("asetNo", maxAssetNo);
			
			this.saveAsetMas(param);
			this.saveAsetDtl(param);
			this.savePoAset(param);			
			
			if(isNew) 
				maxAssetNo++;
		}
		
		return "200";
	}
	
	/** 
	 * TB_PO 정보 등록
	 * */
	public int deletePoInfo(InvestDto invs){
		return mapper.deletePoInfo(invs);
	}
	
	/** 
	 * 신규자산 정보 조회
	 * */
	public AssetMasDto selectAssetMasPoInfo(String assetNo){
		return mapper.selectAssetMas(assetNo);
	}	
	
	/** 
	 * 신규자산 자산유형 특성정보 조회
	 * */	
	public AssetDtlDto selectAssetDtl(String assetNo){
		return mapper.selectAssetDtl(assetNo);
	}		
	

	/** 
	 * 등록된 신규자산 정보 삭제(투자자산/자산마스터/자산상세)
	 * */
	public void deleteNewAset(Map<String, Object> param) {
		mapper.deletePoAset(param);
		mapper.deleteAsetDtl(param);
		mapper.deleteAsetMas(param);
	}	
	
	/** 
	 * 개조자산 등록
	 * */
	public void saveRemodelAset(String userId, List<Map<String, Object>> params){		
		for (Map<String, Object> param : params) {			
			param.put("regr", userId);
			param.put("updr", userId);			
			this.savePoAset(param);
		}
	}		
	
	public List<AssetUploadDto> saveUploadExcel(String userId, List<AssetUploadDto> list) {
		
		//이력삭제
		mapper.deleteUploadHistory(userId);
		
		//업로드 데이터 저장
		for(AssetUploadDto dto : list) {
			dto.setRegr(userId);
			mapper.saveUploadExcel(dto);
		}
		
		//코드정보 조인 후 조회
		List<AssetUploadDto> result = mapper.selectUploadResultList(userId);
		
		//데이터 검증
		for(AssetUploadDto dto : result) {
			dto.setDataVerification();
		}
		
		return result;
	}	
	
	public void saveUploadAset(String userId, List<AssetUploadDto> list) {
		int maxAssetNo = mapper.selectMaxAsetNo();		
		
		for(AssetUploadDto dto : list) {
			
			dto.setAsetNo(Integer.toString(maxAssetNo));
			dto.setRegr(userId);
			dto.setUpdr(userId);
			dto.setQty("1");
			dto.setNewYn("N");

			mapper.saveUploadAsetMas(dto);
			mapper.saveUploadAsetDtl(dto);
			mapper.saveUploadPoAset(dto);
			
			maxAssetNo++;
		}
	}
}
