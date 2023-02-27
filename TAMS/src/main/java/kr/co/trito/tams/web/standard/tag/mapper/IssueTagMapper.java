package kr.co.trito.tams.web.standard.tag.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.asset.insp.manage.dto.InspMasterDto;
import kr.co.trito.tams.web.asset.insp.search.dto.InspSearchMasterDto;
import kr.co.trito.tams.web.standard.tag.dto.IssueTagDto;

@Mapper
public interface IssueTagMapper {

	int selectCountIssueTagList(SearchCondition condition);

	List<IssueTagDto> selectIssueTagList(SearchCondition condition);

	
	

}
