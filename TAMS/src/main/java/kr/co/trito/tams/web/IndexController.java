package kr.co.trito.tams.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.trito.tams.web.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	@Autowired
	UserService userService;
	
    @GetMapping(value = "/")
    public String index(Authentication authentication){
    	UserDetails userInfo = (UserDetails)authentication.getPrincipal();  //userDetail 객체를 가져옴 
    	log.info("=== User Email : {} ===", userInfo.getUsername());
    	
        return "index";
    }        
    
    @GetMapping(value = "/loginView")
    public String loginView(){
        return "login";
    }    
    
    @GetMapping(value="/signupView")
    public String signupView() {    	
        return "signup";
    }	
}
