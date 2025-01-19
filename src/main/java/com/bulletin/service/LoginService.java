package com.bulletin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulletin.entity.UserEntity;
import com.bulletin.repository.LoginMapper;

@Service
public class LoginService {

	@Autowired
	private LoginMapper loginMapper;

	public Object login(UserEntity userEntity) {
		return loginMapper.login(userEntity);
	}
}
