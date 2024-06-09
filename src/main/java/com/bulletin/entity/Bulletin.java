package com.bulletin.entity;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
//掲示板情報Entity
@Data
public class Bulletin {
	
private int seq;
private String title;
private String contents;
private String file_name;
private String create_date;
private String active_yn;
private String startDate;
private String endDate;
private MultipartFile imageFile;
}
