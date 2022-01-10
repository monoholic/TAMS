package kr.co.trito.tams.web.asset.regist.outofbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookBatchDto;
import kr.co.trito.tams.web.asset.regist.outofbook.dto.OutOfBookDto;

@Mapper
public interface OutOfBookMapper {
	
	//부외자산 리스트 검색 카운트
	public int selectCountOutOfBook(SearchCondition condition);
	//부외자산 리스트
	public List<OutOfBookDto> selectOutOfBookList(SearchCondition condition);
	
	//자산번호
	public int selectMaxAsetNo();
	
	//부외자산 등록
	public int saveAsetMas(Map<String, Object> param);
	public int saveAsetDtl(Map<String, Object> param);

	//부외자산 삭제
	public int deleteAsetMas(Map<String, Object> param);	
	public int deleteAsetDtl(Map<String, Object> param);

	//부외자산 업로드 이력 데이터 삭제
	public int deleteUploadHistory(String userId);
	
	//부외자산 업로드 등록
	public int saveUploadExcel(OutOfBookBatchDto dto) ;	
	
	//업로드 결과 조회
	public List<OutOfBookBatchDto> selectUploadResultList(String userId);
}
