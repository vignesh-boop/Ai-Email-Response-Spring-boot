package com.AIemail.Aiemail.app;

import lombok.Data;

@Data
public class EmailRequest {
	private String emailContent;
	private String emailTone;
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getEmailTone() {
		return emailTone;
	}
	public void setEmailTone(String emailTone) {
		this.emailTone = emailTone;
	}

}
