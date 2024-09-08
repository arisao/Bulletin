package com.bulletin.entity;

import lombok.Data;

//掲示板情報Entity
@Data
public class bulletinEdit {

	private int seq;
	private String title;
	private String contents;
	private String imagePath;
	private String create_date;
}
