package com.bulletin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;
import com.bulletin.repository.BulletinMapper;

@Service
public class BulletinService {
	
	@Autowired
	private BulletinMapper bulletinMapper;
	
	public List<Bulletin> search(BulletinSearchRequest bulletinSearchRequest){
		return bulletinMapper.search(bulletinSearchRequest);
	};
	
	public int searchCount(BulletinSearchRequest bulletinSearchRequest){
		return bulletinMapper.searchCount(bulletinSearchRequest);
	};
	

	public int insert(BulletinSearchRequest bulletinSearchRequest){
		return bulletinMapper.insert(bulletinSearchRequest);
	};
}
