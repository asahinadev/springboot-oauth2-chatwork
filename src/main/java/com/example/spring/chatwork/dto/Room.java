package com.example.spring.chatwork.dto;

import java.util.List;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
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
public class Room {

	public enum IconPreset {
		group,
		check,
		document,
		meeting,
		event,
		project,
		business,
		study,
		security,
		star,
		idea,
		heart,
		magcup,
		beer,
		music,
		sports,
		travel;
	}

	public static enum ActionType {
		leave, delete;
	}

	@JsonProperty("room_id")
	Long roomId;

	@JsonProperty("name")
	String name;

	@JsonProperty("type")
	String type;

	@JsonProperty("role")
	String role;

	@JsonProperty("sticky")
	Boolean sticky;

	@JsonProperty("unread_num")
	Long unreadNum;

	@JsonProperty("mention_num")
	Long mentionNum;

	@JsonProperty("mytask_num")
	Long mytaskNum;

	@JsonProperty("message_num")
	Long messageNum;

	@JsonProperty("file_num")
	Long fileNum;

	@JsonProperty("task_num")
	Long taskNum;

	@JsonProperty("icon_path")
	String iconPath;

	@JsonProperty("last_update_time")
	String lastUpdateTime;

	@JsonProperty("description")
	String description;

	@JsonProperty("icon_preset")
	IconPreset iconPreset = IconPreset.group;

	@JsonProperty("link")
	boolean link = false;

	@JsonProperty("link_code")
	String linkCode;

	@JsonProperty("link_need_acceptance")
	boolean linkNeedAcceptance;

	@Size(min = 1)
	@JsonProperty("members_admin_ids")
	List<Integer> membersAdminIds;

	@JsonProperty("members_member_ids")
	List<Integer> membersMemberIds;

	@JsonProperty("members_readonly_ids")
	List<Integer> membersReadonlyIds;

	@JsonProperty("action_type")
	ActionType actionType = ActionType.leave;

	public MultiValueMap<String, String> parameters(ToMapType type) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

		switch (type) {
		case CREATE:
			parameters.add("name", getName());
			parameters.add("description", getDescription());
			if (getIconPreset() != null)
				parameters.add("icon_preset", getIconPreset().name());
			parameters.add("link", "" + isLink());
			parameters.add("link_code", getLinkCode());
			parameters.add("link_need_acceptance", "" + isLinkNeedAcceptance());
			if (getMembersAdminIds() != null)
				parameters.add("members_admin_ids", StringUtils.join(getMembersAdminIds(), ','));
			if (getMembersMemberIds() != null)
				parameters.add("members_member_ids", StringUtils.join(getMembersMemberIds(), ','));
			if (getMembersReadonlyIds() != null)
				parameters.add("members_readonly_ids", StringUtils.join(getMembersReadonlyIds(), ','));
			break;

		case MODIFY:
			parameters.add("name", getName());
			parameters.add("description", getDescription());
			if (getIconPreset() != null)
				parameters.add("icon_preset", getIconPreset().name());
			break;

		case DELETE:
			if (getActionType() != null)
				parameters.add("action_type", getActionType().name());
			break;

		case MEMBERS:
			if (getMembersAdminIds() != null)
				parameters.add("members_admin_ids", StringUtils.join(getMembersAdminIds(), ','));
			if (getMembersMemberIds() != null)
				parameters.add("members_member_ids", StringUtils.join(getMembersMemberIds(), ','));
			if (getMembersReadonlyIds() != null)
				parameters.add("members_readonly_ids", StringUtils.join(getMembersReadonlyIds(), ','));
			break;

		default:
			throw new UnsupportedOperationException(type.toString());
		}

		return parameters;

	}

}
