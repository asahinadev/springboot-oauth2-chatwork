package com.example.spring.chatwork.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

	@JsonProperty("unread_room_num")
	private Long unreadRoom;

	@JsonProperty("mention_room_num")
	private Long mentionRoom;

	@JsonProperty("mytask_room_num")
	private Long mytaskRoom;

	@JsonProperty("unread_num")
	private Long unread;

	@JsonProperty("mention_num")
	private Long mention;

	@JsonProperty("mytask_num")
	private Long mytask;
}
