package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.UserInfoDto;
@Mapper
public interface UserInfoMapper {
	//ユーザーID検索
	UserInfoDto findByUserId(String userId);
	//ユーザーpassword更新
	void updatePassword(@Param("userId") String userId, @Param("password") String password);
}
