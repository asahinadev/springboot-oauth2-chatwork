package com.example.spring.chatwork.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.spring.chatwork.dto.params.ToMapType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

	@JsonProperty("message_id")
	Long messageId;

	@JsonProperty("account")
	User account;

	@JsonProperty("body")
	String body;

	@JsonProperty("send_time")
	Long sendTime;

	@JsonProperty("update_time")
	Long updateTime;

	public MultiValueMap<String, String> parameters(ToMapType type) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

		switch (type) {
		case CREATE:
			parameters.add("body", getBody());
			parameters.add("self_unread", "0");
			break;

		case MODIFY:
			parameters.add("body", getBody());
			break;

		case STATUS:
			parameters.add("message_id", "" + getMessageId());
			break;

		default:
			throw new UnsupportedOperationException();
		}

		return parameters;

	}
}
