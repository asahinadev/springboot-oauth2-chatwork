package com.example.spring.chatwork.oauth2.token;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.RefreshTokenOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Oauth2TokenService {

	private final OAuth2AuthorizedClientService clientService;
	private final RefreshTokenOAuth2AuthorizedClientProvider clientProvider;

	@Autowired
	public Oauth2TokenService(
			OAuth2AuthorizedClientService authorizedClientService) {

		this.clientService = authorizedClientService;
		this.clientProvider = new RefreshTokenOAuth2AuthorizedClientProvider();
	}

	/**
	 * get oauth2 access token.
	 */
	public String getAccessToken() {

		OAuth2AccessToken token = getOAuth2AuthorizedClient().getAccessToken();

		if (isExpiredToken(token)) {
			log.debug("Access Token was expired! {}", token.getExpiresAt());
			token = refreshedAccessToken();
		}

		String value = token.getTokenValue();
		log.debug("Access Token = {}", value);
		return value;
	}

	/**
	 * get oauth2 refresh token.
	 */
	public String getRefreshToken() {

		OAuth2RefreshToken token = getOAuth2AuthorizedClient().getRefreshToken();

		String value = token.getTokenValue();
		log.debug("Refresh Token = {}", value);
		return value;
	}

	/**
	 * access token not expired.
	 */
	private boolean isExpiredToken(OAuth2AccessToken accessToken) {
		return accessToken.getExpiresAt().isBefore(Instant.now());
	}

	private OAuth2AccessToken refreshedAccessToken() {
		log.debug("Refreshing Token Started");

		OAuth2AuthenticationToken authentication = getAuthentication();
		OAuth2AuthorizedClient oldClient = getOAuth2AuthorizedClient();

		OAuth2AuthorizationContext.Builder builder;
		builder = OAuth2AuthorizationContext.withAuthorizedClient(oldClient);

		OAuth2AuthorizationContext context = builder.principal(authentication).build();
		OAuth2AuthorizedClient refreshedClient = clientProvider.authorize(context);

		log.debug("Deleted old authorized AuthorizedClient");
		clientService.removeAuthorizedClient(
				oldClient.getClientRegistration().getRegistrationId(),
				oldClient.getPrincipalName());

		log.debug("Save new authorized AuthorizedClient");
		clientService.saveAuthorizedClient(refreshedClient, authentication);

		log.debug("Refreshing Token Completed");
		return refreshedClient.getAccessToken();
	}

	OAuth2AuthorizedClient getOAuth2AuthorizedClient() {
		log.debug("Get OAuth2AuthorizedClient");
		OAuth2AuthenticationToken auth = getAuthentication();
		return clientService.loadAuthorizedClient(
				auth.getAuthorizedClientRegistrationId(), auth.getName());
	}

	OAuth2AuthenticationToken getAuthentication() {
		log.debug("Get Authentication");
		return (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
	}
}