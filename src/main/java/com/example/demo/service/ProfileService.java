package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserInfoDto;
import com.example.demo.mapper.UserInfoMapper;

import lombok.RequiredArgsConstructor;

@Service
//finalがついたフィールドのコンストラクタを自動生成（DI用）
@RequiredArgsConstructor
public class ProfileService {
	private final UserInfoMapper mapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserInfoDto getUserInfo(String userId) {
		return mapper.findByUserId(userId);
	}
	
	@Transactional
	public void updatePassword(String userId, String rawPassword) {
		// パスワードを暗号化してからMapperに渡す
        String encodedPassword = passwordEncoder.encode(rawPassword);
        mapper.updatePassword(userId, encodedPassword);
	}
	
}
