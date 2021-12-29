package kr.co.trito.tams.web.asset.regist.outofbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.trito.tams.web.asset.regist.outofbook.mapper.OutOfBookMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OutOfBookService {

	@Autowired
	OutOfBookMapper mapper;
}
