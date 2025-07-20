package com.AIemail.Aiemail.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class EmailGeneratorService {
	
	private final WebClient webClient; 
	
	public EmailGeneratorService(WebClient webClient) {
		super();
		this.webClient = webClient;
	}


	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	public String generateEmailReply(EmailRequest emailRequest) {
		// Build the prompt
		String prompt = buildPrompt (emailRequest);
		// Craft a request
		Map<String, Object> requestBody = Map. of (
		 "contents", new Object[] {
		Map. of ( "parts", new Object[] {
		Map. of ("text", prompt)
		})
		}
		);
		
		//Do request to get responce
		String responce = webClient.post().uri(geminiApiUrl + geminiApiKey)
				.header("content-Type","application/json")
				.retrieve()
				.bodyToMono(String.class)
				.block();
		//Return Responce
		
		return extractResponceContent(responce);
	}


	private String extractResponceContent(String responce) {
		try {
			ObjectMapper mapper =new ObjectMapper();
			JsonNode rootNode = mapper.readTree(responce);
			return rootNode.path("candidates")
					.get(0)
					.path("content")
					.path("parts")
					.get(0)
					.path("text")
					.asText();
		}catch(Exception e) {
			return "Error Proceeding"+ e.getMessage();
		}
	
		
	}


	private String buildPrompt(EmailRequest emailRequest) {
		
		StringBuilder prompt = new StringBuilder();
		prompt.append("Generate the proffesinal email for the following content .please dont generate the subject line ");
		
		if(emailRequest.getEmailTone() != null && !emailRequest.getEmailContent().isEmpty() ) {
			prompt.append("Use a ").append(emailRequest.getEmailTone()).append(" tone");
		}
		prompt.append("\nOriginal email :").append(emailRequest.getEmailContent());
		
		return prompt.toString();
		
	}
	

	
}
