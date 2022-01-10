package kr.co.trito.tams.web.asset.regist.outofbook.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import groovy.util.logging.Slf4j;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookBatchDto;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookDto;
import kr.co.trito.tams.web.asset.regist.outofbook.mapper.OutOfBookMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@lombok.extern.slf4j.Slf4j
public class OutOfBookService {

	@Autowired
	OutOfBookMapper mapper;
	
	//부외자산등록 리스트 카운트
	public int selectCountOutOfBook(SearchCondition condition) {
		return mapper.selectCountOutOfBook(condition);
	}	
	
	//부외자산등록 리스트
	public List<OutOfBookDto> selectOutOfBookList(SearchCondition condition) {
		return mapper.selectOutOfBookList(condition);
	}	
	
	//부외자산 저장
	public void saveOutOfBookAset(String userId, List<Map<String, Object>> params) {
		
		int maxAssetNo = mapper.selectMaxAsetNo();
		
		for (Map<String, Object> param : params) {
			
			boolean isNew = StringUtils.isEmpty((String)param.get("asetNo"));				
			
			param.put("aset_out_book_yn", "Y");
			param.put("qty" , "1");
			param.put("regr", userId);
			param.put("updr", userId);
			
			if(isNew) 
				param.put("asetNo", maxAssetNo);
			
			mapper.saveAsetMas(param);
			mapper.saveAsetDtl(param);
			
			if(isNew) 
				maxAssetNo++;
		}
	}	
	
	//부외자산 삭제
	public void deleteOutOfBookAset(Map<String, Object> param) {
		mapper.deleteAsetDtl(param);
		mapper.deleteAsetMas(param);
	}	
	
	//부외자산 업로드 저장
	public List<OutOfBookBatchDto> saveUploadExcel(String userId, List<OutOfBookBatchDto> list) {
		
		//이력삭제
		mapper.deleteUploadHistory(userId);
		
		//업로드 데이터 저장
		for(OutOfBookBatchDto dto : list) {
			dto.setRegr(userId);
			mapper.saveUploadExcel(dto);
		}
		
		//코드정보 조인 후 조회
		List<OutOfBookBatchDto> result = mapper.selectUploadResultList(userId);
		
		//데이터 검증
		for(OutOfBookBatchDto dto : result) {
			dto.setDataVerification();
		}
		
		return result;
	}
}
