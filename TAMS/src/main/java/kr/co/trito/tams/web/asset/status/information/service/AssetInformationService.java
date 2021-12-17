package kr.co.trito.tams.web.asset.status.information.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.status.information.dto.AssetHistoryDto;
import kr.co.trito.tams.web.asset.status.information.dto.AssetInvestDto;
import kr.co.trito.tams.web.asset.status.information.dto.AssetStatusDto;
import kr.co.trito.tams.web.asset.status.information.dto.AssetStatusExcelDto;
import kr.co.trito.tams.web.asset.status.information.mapper.AssetInformationMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AssetInformationService {

	@Autowired
	AssetInformationMapper mapper;
	
	public int selectAssetStatusCount(SearchCondition condition) {
		return mapper.selectAssetStatusCount(condition);
	}	
	
	public List<AssetStatusDto> selectAssetStatusList(SearchCondition condition){
		return mapper.selectAssetStatusList(condition);
	}
	
	public List<AssetStatusExcelDto> selectAssetStatusExcel(SearchCondition condition){
		return mapper.selectAssetStatusExcel(condition);
	}	
	
	public List<AssetHistoryDto> selectAssetHistoryList(String asetNo){
		return mapper.selectAssetHistoryList(asetNo);
	}	
	
	public List<AssetInvestDto> selectAssetInvestList(String asetNo){
		return mapper.selectAssetInvestList(asetNo);
	}			
}