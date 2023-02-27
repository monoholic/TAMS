package kr.co.trito.tams.web.standard.tag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.comm.util.search.SearchCondition;
import kr.co.trito.tams.web.standard.tag.dto.IssueTagDto;
import kr.co.trito.tams.web.standard.tag.mapper.IssueTagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueTagService {

	
	@Autowired
	IssueTagMapper mapper;

	public int selectCountIssueTagList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectCountIssueTagList]");
		return mapper.selectCountIssueTagList(condition);
	}

	public List<IssueTagDto> selectIssueTagList(SearchCondition condition) {
		// TODO Auto-generated method stub
		log.info("[service][selectIssueTagList]");
		return mapper.selectIssueTagList(condition);
	}

	
	
}
