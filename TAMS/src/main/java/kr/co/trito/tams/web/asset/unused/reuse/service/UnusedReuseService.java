package kr.co.trito.tams.web.asset.unused.reuse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.unused.reuse.dto.ReuesReqMasDto;
import kr.co.trito.tams.web.asset.unused.reuse.dto.ReuseAsetReqDto;
import kr.co.trito.tams.web.asset.unused.reuse.mapper.UnusedReuseMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UnusedReuseService {
	
	@Autowired
	UnusedReuseMapper mapper;
	
	public int selectCountUnusedReuseList(SearchCondition condition) {
		return mapper.selectCountUnusedReuseList(condition);
	}
	
	public List<ReuesReqMasDto> selectUnusedReuseList(SearchCondition condition){
		return mapper.selectUnusedReuseList(condition);
	}
	
	public int unusedReuseListInsert(Map<String, Object> data) {
		return mapper.unusedReuseListInsert(data);
	}
	
	public int unusedReuseListDelete(String reqno) {
		return mapper.unusedReuseListDelete(reqno);
	}

	public int unusedReuseListAsetDelete(String reqno) {
		return mapper.unusedReuseListAsetDelete(reqno);
	}
	
	public List<ReuesReqMasDto> selectUnusedReuseRegist(SearchCondition condition) {
		return mapper.selectUnusedReuseRegist(condition);
	}
	
	public int selectCountAssetList(SearchCondition condition) {
		return mapper.selectCountAssetList(condition);
	}
	
	public List<ReuseAsetReqDto> selectAssetList(SearchCondition condition) {
		return mapper.selectAssetList(condition);
	}
	
	public int unusedReuseUpdate1(Map<String, Object> data) {
		return mapper.unusedReuseUpdate1(data);
	}
	
	public int unusedReuseUpdate2(Map<String, Object> data) {
		return mapper.unusedReuseUpdate2(data);
	}	
	
	public int unusedReuseDelete1(Map<String, Object> data) {
		return mapper.unusedReuseDelete1(data);
	}
	
	public int unusedReuseDelete2(ReuseAsetReqDto dto) {
		return mapper.unusedReuseDelete2(dto);
	}
	
	public int unusedReuseDelete3(Map<String, Object> data) {
		return mapper.unusedReuseDelete3(data);
	}
	
	public List<ReuseAsetReqDto> selectReuseUserPopupList(SearchCondition condition){
		return mapper.selectReuseUserPopupList(condition);
	}
}
