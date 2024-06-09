package com.bulletin.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;

@Mapper
public interface BulletinMapper {
	List<Bulletin> search(BulletinSearchRequest bulletin);
	int searchCount(BulletinSearchRequest bulletin);
	Map<String, Object>getArticle(String seq);	
    int insert(BulletinSearchRequest bulletinSearchRequest);
	void deleteArticle(String seq, Bulletin bulletin);

}
