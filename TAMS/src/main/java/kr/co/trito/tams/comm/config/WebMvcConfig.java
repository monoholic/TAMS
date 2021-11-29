package kr.co.trito.tams.comm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import kr.co.trito.tams.comm.interceptor.MenuRoleInterceptor;
//import kr.co.trito.tams.comm.interceptor.CertificationInterceptor;
import kr.co.trito.tams.comm.util.file.excel.view.ExcelXlsView;
import kr.co.trito.tams.comm.util.file.excel.view.ExcelXlsxStreamingView;
import kr.co.trito.tams.comm.util.file.excel.view.ExcelXlsxView;
import lombok.RequiredArgsConstructor;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer{

    private final ExcelXlsView excelXlsView;
    private final ExcelXlsxView excelXlsxView;
    private final ExcelXlsxStreamingView excelXlsxStreamingView;			
	
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { 
			 "classpath:/"
			, "classpath:/static/**"
			, "classpath:/templates/**"
			, "classpath:/resources/"			
			, "classpath:/public/"
			, "classpath:/META-INF/resources/"
			, "classpath:/META-INF/resources/webjars/"
			, "\"/webjars/**\""
			, "swagger-ui.html"	
	};
	
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:/messages/message");
        source.setDefaultEncoding("UTF-8");
        source.setCacheSeconds(60);
        //source.setUseCodeAsDefaultMessage(true);
        return source;
    }        
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }		
	
    @Bean
    public ViewResolver viewResolver() {
    	//응답 시 따로 정해진 결과 페이지가 없는 기술을 사용하려면 반드시 BeanNameViewResolver 필요
    	return new BeanNameViewResolver();
    }     
    
    //thymeleaf layout
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}  
	
    @Override 
    public void addResourceHandlers(ResourceHandlerRegistry registry) { 
       registry.addResourceHandler(CLASSPATH_RESOURCE_LOCATIONS);
       registry.addResourceHandler("swagger-ui.html") 
         .addResourceLocations("classpath:/META-INF/resources/"); 
       registry.addResourceHandler("/webjars/**") 
         .addResourceLocations("classpath:/META-INF/resources/webjars/");       
    }  
    
    /*
     * Menu Role Check Interceptor 설정
     * */
    @Autowired
    MenuRoleInterceptor menuRoleInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(menuRoleInterceptor)
        		.addPathPatterns("/**/*")        
        		.excludePathPatterns("/user/login")
                ;
    }
    
}
