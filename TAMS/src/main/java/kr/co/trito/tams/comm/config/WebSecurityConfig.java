package kr.co.trito.tams.comm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.co.trito.tams.comm.auth.UserAuthenticationFilter;
import kr.co.trito.tams.comm.auth.UserAuthenticationProvider;
import kr.co.trito.tams.comm.auth.UserLoginFailHandler;
import kr.co.trito.tams.comm.auth.UserLoginSuccessHandler;

@EnableWebSecurity 
@Configuration 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean 
	public BCryptPasswordEncoder bCryptPasswordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}

	@Bean public UserLoginSuccessHandler userLoginSuccessHandler() { 
		return new UserLoginSuccessHandler(); 
	}
	
	@Bean 
	public UserAuthenticationFilter userAuthenticationFilter() throws Exception { 
		UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(authenticationManager()); 
		userAuthenticationFilter.setFilterProcessesUrl("/login"); 
		userAuthenticationFilter.setAuthenticationSuccessHandler(userLoginSuccessHandler()); 
		userAuthenticationFilter.afterPropertiesSet(); 
		return userAuthenticationFilter; 
	}

	@Bean 
	public UserAuthenticationProvider userAuthenticationProvider() { 
		return new UserAuthenticationProvider();
	}	
	
	@Bean 
	public UserLoginFailHandler userLoginFailHandler() { 
		return new UserLoginFailHandler();
	}		
	
	@Override 
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) { 
		authenticationManagerBuilder.authenticationProvider(userAuthenticationProvider()); 
	}
	
    @Override
    public void configure(WebSecurity web) {
        web
        	.ignoring()
        	.antMatchers(
        			"/swagger**"
	                ,"/swagger/**" 
	                ,"/swagger-resources/**"
	                ,"/webjars/**"                		
                    ,"/favicon.ico"
	        		,"/v2/api-docs"
	        		,"/v2/api-docs/**"
	        		,"/resources/**"
	        );
    }
    
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 속성을 disable 시킨다. security가 적용된 애플리케이션에서 POST 방식으로 요청시 csrf값을 전송해야 하는데 그러지 않기위해서 disable
			.authorizeRequests() 
			.antMatchers("/loginView", "/signupView", "/user/regist", "/logout").permitAll() 
			.antMatchers("/").hasRole("USER") 
			.antMatchers("/admin").hasRole("ADMIN") 
			.antMatchers("/sample").hasRole("MANAGER")
			.anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
			//.anyRequest().permitAll()
		
	        .and()
	        .formLogin() 
	            .loginPage("/loginView")
	            .usernameParameter("email")
	            .passwordParameter("password")
	            .successHandler(userLoginSuccessHandler())
	            .failureHandler(userLoginFailHandler())
	            
	        .and()
	        .logout()
	        	.logoutUrl("/logout")
	        	.logoutSuccessUrl("/loginView") 
	        	.invalidateHttpSession(true)	
			
	        .and()
			.addFilterBefore(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);	        	
	}    
    
}
