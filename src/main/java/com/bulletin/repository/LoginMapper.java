package com.bulletin.repository;

import org.apache.ibatis.annotations.Mapper;

import com.bulletin.dto.LoginResultDto;
import com.bulletin.entity.UserEntity;

@Mapper
public interface LoginMapper {
	LoginResultDto login(UserEntity userEntity);
}
