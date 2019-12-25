package com.example.spring.chatwork.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.spring.chatwork.dto.params.TaskStatus;
import com.example.spring.chatwork.dto.params.ToMapType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

	@JsonProperty("message_id")
	Long messageId;

	@JsonProperty("task_id")
	Long taskId;

	@JsonProperty("account")
	User account;

	@JsonProperty("assigned_by_account")
	User assignedByAccount;

	@JsonProperty("body")
	String body;

	@JsonProperty("limit_time")
	Long limitTime;

	@JsonProperty("status")
	TaskStatus status;

	@JsonProperty("limit_type")
	String limitType;

	@JsonProperty("to_ids")
	List<String> toIds = new ArrayList<String>();

	public MultiValueMap<String, String> parameters(ToMapType type) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

		switch (type) {
		case LIST:
			if (getAccount() != null)
				parameters.add("account_id", getAccount().getAccountId());
			if (getAssignedByAccount() != null)
				parameters.add("assigned_by_account_id", getAssignedByAccount().getAccountId());
			if (getStatus() != null)
				parameters.add("status", getStatus().name());
			break;

		case CREATE:
			parameters.add("body", getBody());
			parameters.add("limit", "" + getLimitTime());
			parameters.add("limit_type", getLimitType());
			parameters.add("to_ids", StringUtils.join(getToIds(), ','));
			break;

		case MODIFY:
			parameters.add("body", getBody());
			break;

		default:
			throw new UnsupportedOperationException();
		}

		return parameters;

	}
}
