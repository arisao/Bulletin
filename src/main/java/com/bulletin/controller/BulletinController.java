package com.bulletin.controller;

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
	 *  掲示板検索機能
	 *  @param bulletinSearchRequest
	 *  @return 掲示板一覧画面
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
	 *  掲示板登録機能
	 *  @param 
	 *  @return 
	 */
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String insert(
            @RequestPart("image") MultipartFile image,
            @RequestPart("newTitle") String newTitle,
            @RequestPart("newContents") String newContents) {

        BulletinSearchRequest bulletinSearchRequest = new BulletinSearchRequest();
        bulletinSearchRequest.setNewTitle(newTitle);
        bulletinSearchRequest.setNewContents(newContents);
        // Handle the file as needed, e.g., save it to a location or process it

        bulletinService.insert(bulletinSearchRequest);
        return "/bulletin";
    }

	/*
	 *  記事削除
	 *  @param bulletinSearchRequest
	 *  @return 掲示板一覧画面
	 */
	@PutMapping(value = "/delete/{seq}")
	public void delete(@PathVariable String seq, @RequestBody Bulletin bulletin) {
		bulletinService.delete(seq, bulletin);
	}
}
