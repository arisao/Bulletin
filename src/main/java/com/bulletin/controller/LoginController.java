package com.bulletin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bulletin.entity.UserEntity;
import com.bulletin.service.LoginService;

@RestController
public class LoginController {
	/*
	 * ログイン Service
	 */
	@Autowired
	LoginService loginService;

	/*
	 * ログイン
	 * 
	 * @param userEntity
	 * 
	 * @return ログイン結果
	 */
	@PostMapping(value = "/login")
	public Map<String, Object> login(@RequestBody UserEntity userEntity) {
		Map<String, Object> result = new HashMap<>();
		try {
			var response = loginService.login(userEntity);
			result.put("response", response);
		} catch (Exception e) {
			result.put("error", e.getMessage());
		}
		return result;
	}
}