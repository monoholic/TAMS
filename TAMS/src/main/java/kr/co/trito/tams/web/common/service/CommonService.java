package kr.co.trito.tams.web.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.mapper.CommonMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {

	@Autowired
	CommonMapper mapper;
	
	public List<DeptDto> selectDeptPopupList(SearchCondition condition){
		return mapper.selectDeptPopupList(condition);
	}	
	
}