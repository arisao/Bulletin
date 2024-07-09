package com.bulletin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bulletin.dto.BulletinSearchRequest;
import com.bulletin.entity.Bulletin;
import com.bulletin.entity.bulletinEdit;
import com.bulletin.service.BulletinService;

import lombok.extern.java.Log;

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
	 * 検索
	 * 
	 * @param bulletinSearchRequest
	 * 
	 * @return 検索結果一覧
	 */
	@PostMapping(value = "/bulletin")
	public Map<String, Object> search(@RequestBody BulletinSearchRequest bulletinSearchRequest) {
		var response = bulletinService.search(bulletinSearchRequest);
		var totalCount = bulletinService.searchCount(bulletinSearchRequest);
		Map<String, Object> result = new HashMap<>();
		result.put("response", response);
		result.put("totalCount", totalCount);
		return result;
	}

	/*
	 * 記事詳細照会
	 * 
	 * @param seq
	 * 
	 * @return 記事詳細
	 */
	@GetMapping(value = "/detail/{seq}")
	public Map<String, Object> getArticle(@PathVariable("seq") String seq) {
		return bulletinService.getArticle(seq);
	}

	/*
	 * 掲示板登録機能
	 * 
	 * @param
	 * 
	 * @return
	 */
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public String insert(@RequestParam(name = "image", required = false) MultipartFile image,
			@RequestParam("newTitle") String title, @RequestParam("newContents") String contents) {
		try {
			bulletinService.insert(title, contents, image);
			return "Upload successful";
		} catch (IOException e) {
			e.printStackTrace();
			return "Upload failed";
		}
	}

	/*
	 * 記事修正
	 * 
	 * @param bulletinSearchRequest
	 * 
	 * @return 掲示板一覧画面
	 */
	@PutMapping(value = "/edit/{seq}")
	public String edit(@RequestParam(name = "image", required = false) MultipartFile image,
			@RequestParam("title") String title, @RequestParam("contents") String contents, @PathVariable String seq) {
		try {
			bulletinService.editArticle(seq, title, contents, image);
			return "Update successful";
		} catch (IOException e) {
			e.printStackTrace();
			return "Updaate failed";
		}
	}

	/*
	 * 記事削除
	 * 
	 * @param bulletinSearchRequest
	 * 
	 * @return 掲示板一覧画面
	 */
	@PutMapping(value = "/delete/{seq}")
	public void delete(@PathVariable String seq, @RequestBody Bulletin bulletin) {
		bulletinService.delete(seq, bulletin);
	}
}
