package kr.co.trito.tams.web.asset.regist.outofbook.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.trito.tams.web.asset.regist.outofbook.service.OutOfBookService;
import kr.co.trito.tams.web.user.dto.UserDto;
import kr.co.trito.tams.web.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/asset/regist/outofbook")
public class OutOfBookController {

	@Autowired
	OutOfBookService outOfBookService;
	
	private String viewPath = "/content/asset/regist/outofbook/";
	
	@PostMapping("/outOfBook")
	public ModelAndView outOfBookView(Authentication authentication, HttpServletRequest request) {
		ModelAndView view = new ModelAndView();		
		view.addObject("menuId"  , request.getParameter("menuId"));
		view.addObject("menuNm"  , request.getParameter("menuNm"));
		view.addObject("menuDesc", request.getParameter("menuDesc"));
		view.addObject("url"     , request.getParameter("url"));
		UserDto userDto = ((UserInfo) authentication.getPrincipal()).getDto();
		view.addObject("userDto" , userDto);		
		view.setViewName(viewPath.concat("outOfBook"));		
		return view;
	}		
	
	
}
