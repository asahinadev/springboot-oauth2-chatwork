package com.example.spring.chatwork.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.spring.chatwork.oauth.user.ChatworkRequest;
import com.example.spring.chatwork.oauth.user.ChatworkRequestConverter;
import com.example.spring.chatwork.oauth2.token.Oauth2TokenService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatworkApi {

	@Autowired
	Oauth2TokenService tokenService;

	RestTemplate restTemplate = new RestTemplate();
	{
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
	}

	public <T> T get(String url, ParameterizedTypeReference<T> reference) {
		return get(url, null, reference);
	}

	public <T> T get(String url, MultiValueMap<String, String> parameters, ParameterizedTypeReference<T> reference) {

		ChatworkRequest chatworkRequest = ChatworkRequest.builder()
				.accessToken(tokenService.getAccessToken())
				.url(url)
				.method(HttpMethod.GET)
				.build();

		if (parameters != null) {
			chatworkRequest.setParameters(parameters);
		}

		ChatworkRequestConverter converter = new ChatworkRequestConverter();
		RequestEntity<?> request = converter.convert(chatworkRequest);
		return process(request, reference);
	}

	public <T> T post(HttpMethod method, String url, MultiValueMap<String, String> parameters,
			ParameterizedTypeReference<T> reference) {

		ChatworkRequest chatworkRequest = ChatworkRequest.builder()
				.accessToken(tokenService.getAccessToken())
				.url(url)
				.method(method)
				.parameters(parameters)
				.build();

		ChatworkRequestConverter converter = new ChatworkRequestConverter();
		RequestEntity<?> request = converter.convert(chatworkRequest);
		return process(request, reference);
	}

	public <T> T post(String url, ParameterizedTypeReference<T> reference) {
		return post(url, null, reference);
	}

	public <T> T post(String url, MultiValueMap<String, String> parameters, ParameterizedTypeReference<T> reference) {
		return post(HttpMethod.POST, url, parameters, reference);
	}

	public <T> T put(String url, ParameterizedTypeReference<T> reference) {
		return put(url, null, reference);
	}

	public <T> T put(String url, MultiValueMap<String, String> parameters, ParameterizedTypeReference<T> reference) {
		return post(HttpMethod.PUT, url, parameters, reference);
	}

	public <T> T patch(String url, ParameterizedTypeReference<T> reference) {
		return patch(url, null, reference);
	}

	public <T> T patch(String url, MultiValueMap<String, String> parameters, ParameterizedTypeReference<T> reference) {
		return post(HttpMethod.PATCH, url, parameters, reference);
	}

	public <T> T del(String url, ParameterizedTypeReference<T> reference) {
		return del(url, null, reference);
	}

	public <T> T del(String url, MultiValueMap<String, String> parameters, ParameterizedTypeReference<T> reference) {
		return post(HttpMethod.DELETE, url, parameters, reference);
	}

	public <T> T process(RequestEntity<?> request, ParameterizedTypeReference<T> reference) {

		log.debug("request  {}", request.getMethod());
		log.debug("request  {}", request.getUrl());
		log.debug("request  {}", request.getHeaders());
		log.debug("request  {}", request.getBody());

		ResponseEntity<T> response = restTemplate.exchange(request, reference);

		log.debug("responce {}", response.getStatusCode());
		log.debug("responce {}", response.getHeaders());
		log.debug("responce {}", response.getBody());

		return response.getBody();
	}

}
