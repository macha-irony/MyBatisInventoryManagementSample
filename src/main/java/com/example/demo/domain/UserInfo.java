package com.example.demo.domain;

import lombok.Data;

@Data
public class UserInfo {
	private String userId;
	private String password;
	private Long roleId;
}
