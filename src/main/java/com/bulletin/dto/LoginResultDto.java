package com.bulletin.dto;

import lombok.Data;

/*ログイン結果*/
@Data
public class LoginResultDto {

	private String id;
	private String password;
	private String user_name;

}
