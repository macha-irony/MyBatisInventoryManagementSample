package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class PasswordForm {
	@NotBlank(message = "新しいパスワードを入力してください")
    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
    private String newPassword;
}
