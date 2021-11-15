package kr.co.trito.tams.comm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.trito.tams.web.system.user.dto.UserMngDto;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CertificationInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
        HttpSession session = request.getSession();
        
        UserMngDto userInfo = (UserMngDto) session.getAttribute("userInfo"); //로그인 사용자 정보(세션)
        //log.error("@@@@@@ userid "+userInfo.getUsername());
        if(ObjectUtils.isEmpty(userInfo)){ 
        	log.error("세션없음");
            response.sendRedirect("/user/login");
            return false;
        }else{
            session.setMaxInactiveInterval(30*60);
            return true;
        }
        
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
