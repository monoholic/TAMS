package kr.co.trito.tams.comm.log;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Joiner;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
		        .map(entry -> String.format("%s -> (%s)",
		            entry.getKey(), Joiner.on(",").join(entry.getValue())))
		        .collect(Collectors.joining(", "));
	}
	
	//@Pointcut("within(kr.co.trito.tams.web.controller..*)") 
	@Pointcut("execution(* kr.co.trito.tams.web..*Controller.*(..))") 
	public void onRequest() {}
	
	@Around("kr.co.trito.tams.comm.log.LoggingAspect.onRequest()") 
	public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
		String params = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Map<String, String[]> paramMap = request.getParameterMap();
		
		if (paramMap.isEmpty() == false) {
			params = paramMapToString(paramMap);
		}

		long start = System.currentTimeMillis();
		
		try {
			return pjp.proceed(pjp.getArgs()); 
		} finally {
			long end = System.currentTimeMillis();

		    log.info("====== Request Info ======");
		    log.info("Request Method :  {}", pjp.getTarget().getClass().toString() + "." + pjp.getSignature().getName());
		    log.info("Request URL :  {}", request.getRequestURL());
		    log.info("Parameters :  {}", StringUtils.substring(params, 0, 500));
		    log.info("Remote Host :  {}", request.getRemoteHost());
		    log.info("Runtime :  {}ms", end - start); 

			/*
		    LoggingDto dto = LoggingDto.builder()
		    		.userId(userId)
		    		.method(pjp.getTarget().getClass().toString() + "." + pjp.getSignature().getName())
		    		.url(request.getRequestURL().toString())
		    		.params(StringUtils.substring(params, 0, 500))
		    		.host(request.getRemoteHost())
		    		.runtime(end - start + "ms")
		    		.build();
		    loggingService.save(dto); 
			 * */

		}
	}		
	
}