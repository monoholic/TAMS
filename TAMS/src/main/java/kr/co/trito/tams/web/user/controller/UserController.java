package kr.co.trito.tams.web.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.trito.tams.comm.util.res.Response;
import kr.co.trito.tams.comm.util.res.ResponseService;
import kr.co.trito.tams.web.user.dto.UserDto;
import kr.co.trito.tams.web.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	ResponseService responseService;		
	
	@PostMapping("/regist")
	@ResponseBody
	public ResponseEntity<? extends Response> regist(@RequestBody UserDto userDto) { 
	    userService.insertUser(userDto);
		//return new ModelAndView("login");	    
	    return responseService.success(null);
	} 	
	

}

