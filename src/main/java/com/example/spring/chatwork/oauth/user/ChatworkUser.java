package com.example.spring.chatwork.oauth.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatworkUser implements OAuth2User {

	@JsonProperty("account_id")
	private String accountId;

	@JsonProperty("name")
	private String userName;

	@JsonProperty("login_mail")
	private String loginMail;

	@JsonProperty("mail")
	private String mail;

	@JsonProperty("url")
	private String url;

	@JsonProperty("skype")
	private String skype;

	@JsonProperty("facebook")
	private String facebook;

	@JsonProperty("twitter")
	private String twitter;

	@JsonProperty("chatwork_id")
	private String chatworkId;

	@JsonProperty("room_id")
	private Long roomId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("avatar_image_url")
	private String avatar_image_url;

	@JsonProperty("organization_id")
	private Long organizationId;

	@JsonProperty("organization_name")
	private String organizationName;

	@JsonProperty("department")
	private String department;

	@JsonProperty("tel_mobile")
	private String telMobile;

	@JsonProperty("tel_organization")
	private String telOrganization;

	@JsonProperty("tel_extension")
	private String telExtension;

	@JsonProperty("introduction")
	private String introduction;

	@JsonAnySetter
	Map<String, Object> extraParameters = new HashMap<>();

	@Override
	@JsonIgnore
	public String getName() {
		return accountId;
	}

	public String getEmail() {
		return loginMail;
	}

	@Override
	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		return Arrays.asList(
				new OAuth2UserAuthority("CHATWORK_USER", getAttributes()),
				new SimpleGrantedAuthority("USER"));
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getAttributes() {

		Map<String, Object> attributes = new LinkedHashMap<>();

		attributes.put("accountId", accountId);
		attributes.put("twitter", twitter);
		attributes.put("facebook", facebook);
		attributes.put("skype", skype);

		return attributes;
	}

}
