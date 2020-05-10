package com.nasim.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    
    
    
    public ApplicationSecurityConfig(JwtConfig jwtConfig, SecretKey secretKey) {
		super();
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}

//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers( "/api/v1/order/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/order/**").hasAnyRole("STUDENT")
                .anyRequest()
                .authenticated();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        return provider;
//    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	 
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails nasimUser=User.builder()
				.username("nasim")
				.password(passwordEncoder().encode("nasim"))
				.roles("ADMIN")
				.build();
		
		UserDetails shahadatUser=User.builder()
				.username("shahadat")
				.password(passwordEncoder().encode("shahadat"))
				.roles("STUDENT")
				.build();
		return new InMemoryUserDetailsManager(nasimUser,shahadatUser);
	}

}
