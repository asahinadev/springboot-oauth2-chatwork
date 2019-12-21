package com.example.spring.oauth2;

import java.util.Arrays;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.client.RestTemplate;

public class AuthorizationCodeTokenResponseClient
		implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

	DefaultAuthorizationCodeTokenResponseClient defaultClient;

	public AuthorizationCodeTokenResponseClient() {
		RestTemplate template = new RestTemplate(Arrays.asList(
				new FormHttpMessageConverter(),
				new CustomOAuth2AccessTokenResponseHttpMessageConverter()));
		template.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

		defaultClient = new DefaultAuthorizationCodeTokenResponseClient();
		defaultClient.setRestOperations(template);
	}

	@Override
	public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorization) {
		return defaultClient.getTokenResponse(authorization);
	}

}
