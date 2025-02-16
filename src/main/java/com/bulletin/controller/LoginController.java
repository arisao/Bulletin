package com.bulletin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bulletin.entity.UserEntity;
import com.bulletin.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
	public Map<String, Object> login(@RequestBody UserEntity userEntity, HttpServletRequest request) {
		logger.info("ログイン API 呼び出し: id={}", userEntity.getId());

		Map<String, Object> result = new HashMap<>();
		try {
			var response = loginService.login(userEntity);
			if (response == null) {
				logger.warn("ログイン API: ユーザーが見つかりません");
			}
			result.put("response", response);
			HttpSession session = request.getSession();
			session.setAttribute("userId", userEntity.getId());

		} catch (Exception e) {
			logger.error("ログイン処理中にエラーが発生", e);
			result.put("error", e.getMessage());
		}
		return result;
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		logger.info("ログアウト", session.getAttribute("userId"));
		session.invalidate();
		return "/login";

	}

}