package com.example.demo.dto;

import lombok.Data;

@Data
public class UserInfoDto {
	private String userId;
	private String password;
	private Long roleId;
	private String roleName;
}
