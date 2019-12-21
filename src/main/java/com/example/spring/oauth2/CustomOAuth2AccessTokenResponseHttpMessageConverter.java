package com.example.spring.oauth2;

import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomOAuth2AccessTokenResponseHttpMessageConverter
		extends OAuth2AccessTokenResponseHttpMessageConverter {

	private final TypeReference<Map<String, String>> reference = new TypeReference<>() {
	};
	private final ObjectMapper om = new ObjectMapper();

	protected OAuth2AccessTokenResponse readInternal(
			Class<? extends OAuth2AccessTokenResponse> clazz,
			HttpInputMessage body)
			throws HttpMessageNotReadableException {

		Map<String, String> param;
		try {
			param = om.readValue(body.getBody(), reference);

			if (!param.containsKey("token_type")) {
				String beare = TokenType.BEARER.getValue();
				param.put("token_type", beare);
			}

			log.debug("access token response: {}", param);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.warn(e.getMessage(), e);
			} else {
				log.warn(e.getMessage());
			}
			throw new HttpMessageNotReadableException(e.getMessage(), e, body);
		}

		return this.tokenResponseConverter.convert(param);
	}
}