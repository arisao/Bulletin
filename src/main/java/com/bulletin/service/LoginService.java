package com.bulletin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulletin.entity.UserEntity;
import com.bulletin.repository.LoginMapper;

@Service
public class LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private LoginMapper loginMapper;

	public Object login(UserEntity userEntity) {
		logger.info("ログイン試行: id={}, password={}", userEntity.getPassword());
		Object result = loginMapper.login(userEntity);
		if (result == null) {
			logger.warn("ログイン失敗: ユーザーが見つかりません");
		}
		return result;
	}
}
