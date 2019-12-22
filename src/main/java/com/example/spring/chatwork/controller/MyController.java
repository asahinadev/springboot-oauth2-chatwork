package com.example.spring.chatwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.example.spring.chatwork.dto.MyStatusResponse;
import com.example.spring.chatwork.dto.MyTasksResponse;
import com.example.spring.chatwork.oauth.user.ChatworkRequest;
import com.example.spring.chatwork.oauth.user.ChatworkRequestConverter;
import com.example.spring.chatwork.oauth.user.ChatworkUser;
import com.example.spring.chatwork.oauth2.token.Oauth2TokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/my")
public class MyController {

	@Autowired
	Oauth2TokenService tokenService;

	@GetMapping("status")
	@ResponseBody
	public MyStatusResponse status() {

		ChatworkRequest chatworkRequest = ChatworkRequest.builder()
				.accessToken(tokenService.getAccessToken())
				.url("https://api.chatwork.com/v2/my/status")
				.method(HttpMethod.GET)
				.build();

		ChatworkRequestConverter converter = new ChatworkRequestConverter();

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

		RequestEntity<?> request = converter.convert(chatworkRequest);

		log.debug("request  {}", request.getMethod());
		log.debug("request  {}", request.getUrl());
		log.debug("request  {}", request.getHeaders());
		log.debug("request  {}", request.getBody());

		ResponseEntity<MyStatusResponse> response = restTemplate.exchange(request, MyStatusResponse.class);

		log.debug("responce {}", response.getStatusCode());
		log.debug("responce {}", response.getHeaders());
		log.debug("responce {}", response.getBody());

		return response.getBody();
	}

	@GetMapping("tasks")
	@ResponseBody
	public List<MyTasksResponse> tasks(
			@AuthenticationPrincipal ChatworkUser user,
			@RequestParam(required = false) List<String> status) {

		ChatworkRequest chatworkRequest = ChatworkRequest.builder()
				.accessToken(tokenService.getAccessToken())
				.url("https://api.chatwork.com/v2/my/tasks")
				.method(HttpMethod.GET)
				.build();

		chatworkRequest.getParameters().add("assigned_by_account_id", "" + user.getAccountId());
		if (status != null) {
			chatworkRequest.getParameters().put("status", status);
		}

		ChatworkRequestConverter converter = new ChatworkRequestConverter();

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

		RequestEntity<?> request = converter.convert(chatworkRequest);

		log.debug("request  {}", request.getMethod());
		log.debug("request  {}", request.getUrl());
		log.debug("request  {}", request.getHeaders());
		log.debug("request  {}", request.getBody());

		ParameterizedTypeReference<List<MyTasksResponse>> reference = new ParameterizedTypeReference<List<MyTasksResponse>>() {
		};

		ResponseEntity<List<MyTasksResponse>> response = restTemplate.exchange(request, reference);

		log.debug("responce {}", response.getStatusCode());
		log.debug("responce {}", response.getHeaders());
		log.debug("responce {}", response.getBody());

		return response.getBody();
	}

}
