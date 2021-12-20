package kr.co.trito.tams.web.asset.change.modify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasDto;
import kr.co.trito.tams.web.asset.change.modify.dto.ReqMasExcelDto;
import kr.co.trito.tams.web.asset.change.modify.mapper.ReqMasMapper;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReqMasService {
	
	@Autowired
	ReqMasMapper mapper;
	
	public int selectCountRequestList(SearchCondition condition) {
		return mapper.selectCountRequestList(condition);
	}
	
	public List<ReqMasDto> selectRequestList(SearchCondition condition){
		return mapper.selectRequestList(condition);
	}
	
	public List<ReqMasExcelDto> selectReqExcelList(SearchCondition condition) {
		return mapper.selectReqExcelList(condition);
	}
	
	public int requestListInsert(ReqMasDto dto) {
		return mapper.requestListInsert(dto);				
	}
	
	public int requestListDelete(ReqMasDto dto) {
		return mapper.requestListDelete(dto);
	}
	
	public List<CodeDto> selectReqStusList(String email) {
		return mapper.selectReqStusList(email);
	}
	
	public List<ReqMasDto> selectRequestRegist(SearchCondition condition) {
		return mapper.selectRequestRegist(condition);
	}
	
	public List<AsetMasDto> selectAsetReqList(SearchCondition condition) {
		return mapper.selectAsetReqList(condition);
	}
}
