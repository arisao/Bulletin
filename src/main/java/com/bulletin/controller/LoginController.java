package com.bulletin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bulletin.entity.UserEntity;
import com.bulletin.service.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	public Map<String, Object> login(@RequestBody UserEntity userEntity, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("ログイン API 呼び出し: id={}", userEntity.getId());

		Map<String, Object> result = new HashMap<>();
		try {
			var responseData = loginService.login(userEntity);
			if (responseData == null) {
				logger.warn("ログイン API: ユーザーが見つかりません");
			}
			result.put("response", responseData);
			// セッションを生成、クライアントサイドからヘッダーの情報をとってくる。
			HttpSession session = request.getSession(true);
			logger.info("セッションID: {}", session.getId());
			// key, value
			session.setAttribute("userId", userEntity.getId());
			//
			// Set-Cookie クッキーをヘッダーに詰めているそれがクライアントにかえる
			response.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly; SameSite=LAX;");

			logger.info("Set-Cookie ヘッダー設定: JSESSIONID={}", session.getId());
		} catch (Exception e) {
			logger.error("ログイン処理中にエラーが発生", e);
			result.put("error", e.getMessage());
		}
		return result;
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object userId = session.getAttribute("userId");
			logger.info("ログアウト: userId={}", userId);
			// invalidate前にログ出力
			logger.info("ログアウト時のセッションID: {}", session.getId());
			session.invalidate();
			logger.info("セッションを無効化しました: sessionId={}", session.getId());
			// セッションを完全にリセットするために新しいセッションを生成
			request.getSession(true);
		} else {
			logger.info("セッションなしでログアウト処理");
		}

		// JSESSIONID クッキーを削除
		Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		return "/login";

	}

	/*
	 * sessionが有効かを判断
	 * 
	 * @param session
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/check-session")
	public ResponseEntity<?> checkSession(HttpSession session) {
		if (session.getAttribute("user") == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		}
		return ResponseEntity.ok().body("Authenticated");
	}

}