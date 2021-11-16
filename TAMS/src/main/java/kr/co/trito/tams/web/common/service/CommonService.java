package kr.co.trito.tams.web.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.mapper.CommonMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommonService {

	@Autowired
	CommonMapper mapper;
	
	/** 
	 * 부서팝업 조회 
	 * */
	public List<DeptDto> selectDeptPopupList(SearchCondition condition){
		return mapper.selectDeptPopupList(condition);
	}
	
	
	
	/** 
	 * 첨부파일 정보 조회 
	 * */
	public List<FileDto> selectFileList(SearchCondition condition){
		return mapper.selectFileList(condition);
	}
	
	/** 
	 * 파일정보 저장
	 * */
	public int saveFiles(FileDto dto){
		return mapper.saveFileInfo(dto);
	}
	
	/** 
	 * 다운로드 횟수 업데이트
	 * */
	public int updateDwldCnt(FileDto dto){
		return mapper.updateDwldCnt(dto);
	}	
	
}