package NaverDevelopers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.headers().frameOptions().disable()
			.and().authorizeRequests() //요청한 주소에 대한 권한설정
					.antMatchers("/", "/css/**" , "/images/**", "/js/**").permitAll();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
	}

	
}
