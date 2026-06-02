package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserInfoDto;
import com.example.demo.mapper.UserInfoMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 1. DBからユーザーを取得
        UserInfoDto user = mapper.findByUserId(userId);
        
        // 2. ユーザーが存在しない場合の例外処理
        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + userId);
        }

        // 3. DBから取ってきた値を Spring Security の User クラスに変換
        //    ここでパスワードの照合チェック（BCrypt）をSpring Securityが自動で行います
        return User.withUsername(user.getUserId())
                   .password(user.getPassword()) // ここでハッシュ値と比較される
                   .roles(getRoleName(user.getRoleId())) // 権限の設定
                   .build();
    }

    // role_id を Spring Security が理解できる文字列に変換
    private String getRoleName(Long roleId) {
        if (roleId == 1) return "ADMIN";
        return "USER";
    }
}