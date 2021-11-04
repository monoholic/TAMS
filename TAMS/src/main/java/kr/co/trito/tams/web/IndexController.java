package kr.co.trito.tams.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.co.trito.tams.web.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	@Autowired
	UserService userService;
	
    @GetMapping(value = "/")
    public String index(Authentication authentication){
        return "index";
    }        
    
    @GetMapping(value = "/sample")
    public String sample(Authentication authentication){
        return "/content/sample/sample";
    }
    
    @GetMapping(value = "/jsgrid")
    public String jsgrid(Authentication authentication){
        return "/content/sample/jsgrid";
    }                

    @GetMapping(value = "/ex_source")
    public String source(){
        return "/content/sample/source";
    } 
    
    @GetMapping(value = "/user/login")
    public String loginView(){
        return "/login";
    }    
    
    @GetMapping(value="/user/signup")
    public String signupView() {    	
        return "/signup";
    }	
    
    @GetMapping(value="/test")
    public String testView(Model model) {    	
    	model.addAttribute("test1", "TestString 1");
    	model.addAttribute("test2", "TestString 2");
        return "content/home";
    }	    
}
