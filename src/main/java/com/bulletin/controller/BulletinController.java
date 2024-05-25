package com.bulletin.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;
import com.bulletin.service.BulletinService;

/*
 * 掲示板 Controller
 * */
@CrossOrigin(origins = "http:localhost:8800")
@RestController
public class BulletinController {
	/*
	 * 掲示板 Service
	 */	
	@Autowired
	BulletinService bulletinService;

	@GetMapping(value = "/")
	public String home() {
		return "test...";
	}

	
	/*
	 *  掲示板検索機能
	 *  @param bulletinSearchRequest
	 *  @return 掲示板一覧画面
	 */
	@PostMapping(value = "/bulletin")
	public List<Bulletin> search(@RequestBody BulletinSearchRequest bulletinSearchRequest) {
		var result = bulletinService.search(bulletinSearchRequest);
	    return result;
	}

}
