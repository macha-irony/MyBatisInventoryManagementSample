package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
				.anyRequest().authenticated()
				)
		.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login") // ★ここを明示すると動作が安定します
				.usernameParameter("username") // ★HTMLのname属性と一致させる
				.passwordParameter("password") // ★HTMLのname属性と一致させる
				.defaultSuccessUrl("/", true)
				.permitAll()
				)
		.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				);

		return http.build();
	}

	//    @Bean　20260608デバック用
	//    public PasswordEncoder passwordEncoder() {
	//        return new PasswordEncoder() {
	//            @Override
	//            public String encode(CharSequence rawPassword) {
	//                return rawPassword.toString();
	//            }
	//
	//            @Override
	//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
	//                // ここで強制的に true を返す（パスワード比較をスキップ）
	//                System.out.println("★デバッグ: 入力=" + rawPassword + ", DB値=" + encodedPassword);
	//                return true; 
	//            }
	//        };
	//    }



	@Value("${spring.profiles.active:default}")
	private String activeProfile;

	@Bean
	public PasswordEncoder passwordEncoder() {
	    if ("dev".equals(activeProfile)) {
	        return NoOpPasswordEncoder.getInstance();
	    }
	    return new BCryptPasswordEncoder();
	}

}