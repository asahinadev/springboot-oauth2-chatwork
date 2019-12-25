package com.example.spring.chatwork.oauth.user;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.chatwork.dto.ChatworkUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatworkUserService
		implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final RestOperations restOperations;

	public ChatworkUserService() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
		this.restOperations = restTemplate;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		Assert.notNull(userRequest, "userRequest cannot be null");

		ClientRegistration client = userRequest.getClientRegistration();
		String clientId = client.getClientName();

		log.debug("Chatwork? ({})", clientId);
		if (!Objects.equals("Chatwork", clientId)) {
			return null;
		}

		HttpMethod httpMethod = HttpMethod.GET;

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userRequest.getAccessToken().getTokenValue());
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		URI uri = UriComponentsBuilder.fromUriString(
				client.getProviderDetails().getUserInfoEndpoint().getUri())
				.build().toUri();

		RequestEntity<?> request = new RequestEntity<>(headers, httpMethod, uri);

		log.debug("request  {}", request.getMethod());
		log.debug("request  {}", request.getUrl());
		log.debug("request  {}", request.getHeaders());
		log.debug("request  {}", request.getBody());

		ResponseEntity<ChatworkUser> response = this.restOperations.exchange(request, ChatworkUser.class);

		log.debug("responce {}", response.getStatusCode());
		log.debug("responce {}", response.getHeaders());
		log.debug("responce {}", response.getBody());

		return response.getBody();

	}

}
