package kr.co.trito.tams.web.asset.unused.regist.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistAsetReqDto;
import kr.co.trito.tams.web.asset.unused.regist.dto.RegistReqMasDto;

@Mapper
public interface UnusedRegistMapper {
	
	public int selectCountUnusedRegistList(SearchCondition condition);
	
	public List<RegistReqMasDto> selectUnusedRegistList(SearchCondition condition);
	
	public int unusedRegistListInsert(Map<String, Object> data);
	
	public int unusedRegistListDelete(String reqno);
	
	public int unusedRegistListAsetDelete(String reqno);
	
	public List<RegistReqMasDto> selectUnusedRegistRegist(SearchCondition condition);
	
	public int selectCountAssetList(SearchCondition condition);
	
	public List<RegistAsetReqDto> selectAssetList(SearchCondition condition);
	
	public int unusedRegistUpdate1(Map<String, Object> data);
	
	public int unusedRegistUpdate2(Map<String, Object> data);

	public int unusedRegistDelete1(Map<String, Object> data);
	
	public int unusedRegistDelete2(RegistAsetReqDto dto);
	
	public int unusedRegistDelete3(Map<String, Object> data);
}
