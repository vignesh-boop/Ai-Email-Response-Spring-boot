package com.AIemail.Aiemail.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailGenerateController {
	private final EmailGeneratorService emailGeneratorService ;
	
	
	public EmailGenerateController(EmailGeneratorService emailGeneratorService) {
		super();
		this.emailGeneratorService = emailGeneratorService;
	}


	@PostMapping("/generate")
	public ResponseEntity<String>generateEmail(@RequestBody EmailRequest emailRequest){
	String 	response = emailGeneratorService.generateEmailReply(emailRequest);
		return ResponseEntity.ok(response);	
	}

}
