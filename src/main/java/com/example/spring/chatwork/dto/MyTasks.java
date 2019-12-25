package com.example.spring.chatwork.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyTasks {

	@JsonProperty("task_id")
	private Long taskId;

	@JsonProperty("message_id")
	private String messageId;

	@JsonProperty("body")
	private String body;

	@JsonProperty("limit_time")
	private String limitTime;

	@JsonProperty("status")
	private String status;

	@JsonProperty("limit_type")
	private String limitType;

}
