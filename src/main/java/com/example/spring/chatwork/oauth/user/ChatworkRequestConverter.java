package com.example.spring.chatwork.oauth.user;

import java.net.URI;
import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class ChatworkRequestConverter
		implements Converter<ChatworkRequest, RequestEntity<?>> {

	@Override
	public RequestEntity<?> convert(ChatworkRequest request) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(request.getAccessToken());
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getUrl());

		URI uri;
		switch (request.getMethod()) {
		case GET:
			builder.queryParams(request.getParameters());
			break;

		case POST:
			break;

		default:
			throw new UnsupportedOperationException();
		}
		uri = builder.build().toUri();

		return new RequestEntity<>(headers, request.getMethod(), uri);

	}
}