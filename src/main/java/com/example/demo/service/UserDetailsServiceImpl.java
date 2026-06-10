package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserInfoDto;
import com.example.demo.mapper.UserInfoMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserInfoMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserInfoDto user = mapper.findByUserId(userId);
		if (user == null) throw new UsernameNotFoundException("ユーザーがいません");

		// ログにDBの値を出力
		System.out.println("★DEBUG: DBのユーザーID: " + user.getUserId());
		System.out.println("★DEBUG: DBのパスワード(ハッシュ): " + user.getPassword());

//		 ★ここでDBの平文をBCryptでハッシュ化してSpringに渡す
		String hashedPassword = passwordEncoder.encode(user.getPassword());

		return User.withUsername(user.getUserId())
				//.password(hashedPassword)
				.password(user.getPassword())
				.authorities("ROLE_ADMIN")
				.build();
	}

	//    @Override　20260608デバック用にコメントアウト
	//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
	//        UserInfoDto user = mapper.findByUserId(userId);
	//        
	//        if (user == null) {
	//            throw new UsernameNotFoundException("ユーザーが見つかりません: " + userId);
	//        }
	//
	//        // ★重要: roles() ではなく authorities() を明示的に使用
	//        // これにより "ROLE_" の自動付与による不整合を防げます
	//        String roleName = (user.getRoleId() != null && user.getRoleId() == 1) ? "ROLE_ADMIN" : "ROLE_USER";
	//
	//        return User.withUsername(user.getUserId())
	//                   .password(user.getPassword())
	//                   .authorities(roleName) 
	//                   .build();
	//    }

	// role_id を Spring Security が理解できる文字列に変換
	private String getRoleName(Long roleId) {
		if (roleId == 1) return "ADMIN";
		return "USER";
	}
}