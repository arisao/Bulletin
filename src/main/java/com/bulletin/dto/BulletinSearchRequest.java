package com.bulletin.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/*掲示板検索用requestData*/
@Data
public class BulletinSearchRequest{
	
	private int seq;
	private String title;
	private String contents;
	private String startDate;
	private String endDate;
	private String update_date;
	private String newTitle;
    private String newContents;
    private String image;
	
	

}
