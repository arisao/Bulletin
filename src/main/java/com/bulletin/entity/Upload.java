package com.bulletin.entity;

import lombok.Data;

//掲示板情報Entity
@Data
public class Upload {

	private int seq;
	private String title;
	private String contents;
	private String create_date;
	private String active_yn;
	private String imagePath;

}
