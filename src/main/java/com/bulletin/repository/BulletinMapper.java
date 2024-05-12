package com.bulletin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;

@Mapper
public interface BulletinMapper {
	List<Bulletin> search(BulletinSearchRequest bulletin);
	void insert(Bulletin bulletin);
	

}
