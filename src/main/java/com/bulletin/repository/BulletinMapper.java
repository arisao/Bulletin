package com.bulletin.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;
import com.bulletin.entity.Upload;
import com.bulletin.entity.bulletinEdit;

@Mapper
public interface BulletinMapper {
	List<Bulletin> search(BulletinSearchRequest bulletin);

	int searchCount(BulletinSearchRequest bulletin);

	Map<String, Object> getArticle(String seq);

	void insert(Upload upload);

	void deleteArticle(String seq, Bulletin bulletin);

	int editArticle(@Param("seq") String seq, @Param("bulletin") bulletinEdit bulletin);
}
