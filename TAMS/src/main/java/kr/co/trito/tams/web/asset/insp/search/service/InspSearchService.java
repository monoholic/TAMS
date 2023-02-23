package kr.co.trito.tams.web.asset.insp.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.web.asset.insp.search.mapper.InspSearchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class InspSearchService {

	
	@Autowired
	InspSearchMapper mapper;
	
	
	
}
