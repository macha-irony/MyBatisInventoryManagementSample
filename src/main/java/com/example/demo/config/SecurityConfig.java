package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // ★追加: ログインページとCSS等の静的リソースはログインなしでアクセス許可
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // ★ここを自分の作成したパスに設定
                .loginPage("/login") 
                // ログイン成功時に飛ばす先（例: 商品一覧）
                .defaultSuccessUrl("/", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // ログアウト後に戻る先
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}