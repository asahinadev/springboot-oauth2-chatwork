package com.example.spring.chatwork.oauth2.authorization;

import java.util.Arrays;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.client.RestTemplate;

import com.example.spring.chatwork.oauth2.token.AccessTokenConverter;

public class AuthorizationResponseClient
		implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

	DefaultAuthorizationCodeTokenResponseClient defaultClient;

	public AuthorizationResponseClient() {
		RestTemplate template = new RestTemplate(Arrays.asList(
				new FormHttpMessageConverter(),
				new AccessTokenConverter()));
		template.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

		defaultClient = new DefaultAuthorizationCodeTokenResponseClient();
		defaultClient.setRestOperations(template);
	}

	@Override
	public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorization) {
		return defaultClient.getTokenResponse(authorization);
	}

}
