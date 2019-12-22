package com.example.spring.chatwork.oauth.user;

import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatworkRequest {

	String accessToken;

	String url;

	@Builder.Default
	HttpMethod method = HttpMethod.GET;

	@Builder.Default
	MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
}
