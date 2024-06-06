package com.bulletin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;

@Mapper
public interface BulletinMapper {
	List<Bulletin> search(BulletinSearchRequest bulletin);
	int searchCount(BulletinSearchRequest bulletin);
    int insert(BulletinSearchRequest bulletinSearchRequest);
	void deleteArticle(String seq, Bulletin bulletin);

}
