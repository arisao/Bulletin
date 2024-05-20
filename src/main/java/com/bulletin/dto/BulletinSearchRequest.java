package com.bulletin.dto;

import java.io.Serializable;

import lombok.Data;

/*掲示板検索用requestData*/
@Data
public class BulletinSearchRequest{
	
	private int seq;
	private String title;
	private String contents;
	private String create_date;
	private String update_date;
	

}
