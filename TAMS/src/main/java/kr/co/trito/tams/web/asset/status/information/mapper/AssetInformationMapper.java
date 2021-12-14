package kr.co.trito.tams.web.asset.status.information.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.status.information.dto.AssetStatusDto;
import kr.co.trito.tams.web.asset.status.information.dto.AssetStatusExcelDto;

@Mapper
public interface AssetInformationMapper {
	public int selectAssetStatusCount(SearchCondition condition);
	public List<AssetStatusDto> selectAssetStatusList(SearchCondition condition);
	public List<AssetStatusExcelDto> selectAssetStatusExcel(SearchCondition condition);
}
