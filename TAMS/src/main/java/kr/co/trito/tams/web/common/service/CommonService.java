package kr.co.trito.tams.web.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.file.FileDto;
import kr.co.trito.tams.comm.util.file.excel.InvDto;
import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.common.dto.AsetMasDto;
import kr.co.trito.tams.web.common.dto.CodeTreeDto;
import kr.co.trito.tams.web.common.dto.ComCodeDto;
import kr.co.trito.tams.web.common.dto.ComCodeParamDto;
import kr.co.trito.tams.web.common.dto.DeptDto;
import kr.co.trito.tams.web.common.dto.MenuRoleCheckDto;
import kr.co.trito.tams.web.common.mapper.CommonMapper;
import kr.co.trito.tams.web.standard.code.dto.CodeDto;
import kr.co.trito.tams.web.system.user.dto.UserMngDto;
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
	 * 부서팝업(트리) 조회
	 * */
	public List<CodeTreeDto> selectDeptTree(SearchCondition condtion){
		return mapper.selectDeptTree(condtion);
	}
	
	/** 
	 * 사용자팝업 조회 
	 * */
	public List<UserMngDto> selectUserPopupList(SearchCondition condition){
		return mapper.selectUserPopupList(condition);
	}
	
	/** 
	 * 사용자필터팝업 조회 
	 * */
	public List<UserMngDto> selectUserFilterPopupList(SearchCondition condition){
		return mapper.selectUserFilterPopupList(condition);
	}	
	
	/** 
	 * 공통) 필터 데이터 조회
	 * */
	public List<ComCodeDto> commSelectBox(ComCodeParamDto params){
		return mapper.commSelectBox(params);
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
	 * 파일정보 삭제
	 * */
	public int deleteFiles(FileDto dto){
		return mapper.deleteFile(dto);
	}
	
	/** 
	 * 다운로드 횟수 업데이트
	 * */
	public int updateDwldCnt(FileDto dto){
		return mapper.updateDwldCnt(dto);
	}	
	
	/** 
	 * 화면 권한 조회
	 * */
	public MenuRoleCheckDto selectMenuRoleCheck(MenuRoleCheckDto dto){
		return mapper.selectMenuRoleCheck(dto);
	}	
	
	/** 
	 * 사용자 부서정보 조회 (사업부, 부서)
	 * */
	public Map<String,String> selectUserDeptInfo(Map<String,String> map){
		return mapper.selectUserDeptInfo(map);
	}	
	
	/** 
	 * TB_INV_MAS 정보 등록
	 * */
	public int saveInvestInfo(InvDto invs){
		return mapper.saveInvestInfo(invs);
	}
	
	/** 
	 * TB_PO 정보 등록
	 * */
	public int savePoInfo(List<InvDto> invs){
		return mapper.savePoInfo(invs);
	}	
	
	/** 
	 * 공통코드(트리) 조회
	 * */
	public List<CodeTreeDto> selectCodeTree(Map<String,String> map){
		return mapper.selectCodeTree(map);
	}	
	
	
	/** 
	 * 챠트샘플 조회
	 * */
	public List<Map<String,String>> selectChartData(String gbn){
		List<Map<String,String>> result = null;
		if( "chart1".equals(gbn) ) {
			result = mapper.selectChart1();
		} else {
			result = mapper.selectChart2();
		}
		return result;
	}	
	
	/** 
	 * 자산선택(팝업-공통) 조회
	 * */
	public List<AsetMasDto> selectAsetMasList(SearchCondition condition){
		return mapper.selectAsetMasList(condition);
	}
	
	/** 
	 * 자산선택(팝업-공통) 조회
	 * */
	public int selectCountAsetMas(SearchCondition condition) {
		return mapper.selectCountAsetMas(condition);
	}
	
	/** 
	 * 자산선택(팝업-공통) 자산유형 조회
	 * */
	public List<CodeDto> selectAsetType(String email){
		return mapper.selectAsetType(email);
	}
}