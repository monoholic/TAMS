package kr.co.trito.tams.web.aset.update.reqmas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.aset.update.reqmas.dto.ReqMasDto;
import kr.co.trito.tams.web.aset.update.reqmas.mapper.ReqMasMapper;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReqMasService {
	
	@Autowired
	ReqMasMapper mapper;
	
	public int selectCountReqInqr(SearchCondition condition) {
		return mapper.selectCountReqInqr(condition);
	}
	
	public List<ReqMasDto> selectReqInqr(SearchCondition condition){
		return mapper.selectReqInqr(condition);
	}
	
	public int reqInqrInsert(ReqMasDto dto) {
		return mapper.reqInqrInsert(dto);				
	}
	
	public int reqInqrUpdate(ReqMasDto dto) {
		return mapper.reqInqrUpdate(dto);
	}
	
	public int reqInqrDelete(ReqMasDto dto) {
		return mapper.reqInqrDelete(dto);
	}
	
	public List<CodeDto> selectReqTypeList(String email){
		return mapper.selectReqTypeList(email);
	}
	
	public List<CodeDto> selectReqStusList(String email){
		return mapper.selectReqStusList(email);
	}
}
