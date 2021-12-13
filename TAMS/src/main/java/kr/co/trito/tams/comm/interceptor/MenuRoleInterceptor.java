package kr.co.trito.tams.comm.interceptor;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.trito.tams.web.common.dto.MenuRoleCheckDto;
import kr.co.trito.tams.web.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MenuRoleInterceptor implements HandlerInterceptor {
	
	@Autowired
	CommonService commonService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
		log.error(" MenuRoleInterceptor -> preHandle ");
		
		HttpSession session = request.getSession();
		
		//인증정보를 가져온다.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//인증정보가 있을 때만 조회한다 
		if( auth != null ) {
			
			List<GrantedAuthority> userRole = new ArrayList<>(auth.getAuthorities());
			
			String role = userRole.get(0).getAuthority();
			//log.error("@@@@@@@ userRole : "+ role);
			
			String menuId = request.getParameter("menuId");
			//log.error("@@@@@@@@@ menu id ::: "+menuId);
			
			MenuRoleCheckDto dto = new MenuRoleCheckDto();
			dto.setRoleId(role);
			dto.setMenuId(menuId);
			
			MenuRoleCheckDto roleDto = commonService.selectMenuRoleCheck(dto);
			//log.error("@@@@ "+roleDto);
			session.setAttribute("MenuRoleCheck", roleDto);		
			
		}
		
        return true;
        
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        
    }

}
