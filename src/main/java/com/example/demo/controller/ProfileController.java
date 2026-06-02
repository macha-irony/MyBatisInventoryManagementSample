package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.UserInfoDto;
import com.example.demo.form.PasswordForm;
import com.example.demo.service.ProfileService;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor 
public class ProfileController {
	private final ProfileService service;
	
	@GetMapping("/profile")
	public String showProfile(Authentication authentication, Model model) {
		String userId = authentication.getName();
		UserInfoDto userInfo = service.getUserInfo(userId);
		// 【重要】ここで必ずフォームオブジェクトを生成して Model に渡す！
	    // 名前は HTML の th:object="${passwordForm}" と完全に一致させること
	    model.addAttribute("passwordForm", new PasswordForm());
        model.addAttribute("userId", userId);
        // 権限情報も取得可能
        //model.addAttribute("roles", authentication.getAuthorities());
        model.addAttribute("roles",userInfo.getRoleName());
        
        return "profile"; // templates/profile.html を表示
	}
	
	@PostMapping("/profile/updatePassword")
	public String updatePassword(Authentication auth, 
								@Valid PasswordForm form, 
								BindingResult result,
								Model model) {
		// バリデーションエラーがあるかチェック
	    if (result.hasErrors()) {
	        return "profile"; // エラーがあれば、プロフィール画面に戻す
	    }
		service.updatePassword(auth.getName(), form.getNewPassword());
	    return "redirect:/profile";
	}
}
