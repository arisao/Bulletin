package com.bulletin.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bulletin.entity.UserEntity;

@Mapper
public interface LoginMapper {
	Map<String, Object> login(UserEntity userEntity);
}
