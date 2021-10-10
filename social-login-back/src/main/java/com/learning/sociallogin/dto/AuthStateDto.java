package com.learning.sociallogin.dto;

import lombok.Data;

@Data
public class AuthStateDto {

	private Integer id;
	private String google_id;
	private String token;
	private String first_name;
	private String last_name;
	private String photo_url;

}
