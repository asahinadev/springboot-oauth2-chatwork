package com.example.spring.chatwork.oauth.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatworkUser
		implements OAuth2User, Map<String, Object> {

	@JsonAnySetter
	Map<String, Object> extraParameters = new HashMap<>();

	@Override
	@JsonIgnore
	public String getName() {
		return String.valueOf(get("account_id"));
	}

	public String getEmail() {
		return String.valueOf(get("login_mail"));
	}

	@Override
	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		return Arrays.asList(
				new OAuth2UserAuthority("USER", getAttributes()),
				new SimpleGrantedAuthority("USER"));
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getAttributes() {
		return this.getExtraParameters();
	}

	public int size() {
		return extraParameters.size();
	}

	public boolean isEmpty() {
		return extraParameters.isEmpty();
	}

	public boolean containsKey(Object key) {
		return extraParameters.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return extraParameters.containsValue(value);
	}

	public Object get(Object key) {
		return extraParameters.get(key);
	}

	public Object put(String key, Object value) {
		return extraParameters.put(key, value);
	}

	public Object remove(Object key) {
		return extraParameters.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		extraParameters.putAll(m);
	}

	public void clear() {
		extraParameters.clear();
	}

	public Set<String> keySet() {
		return extraParameters.keySet();
	}

	public Collection<Object> values() {
		return extraParameters.values();
	}

	public Set<Entry<String, Object>> entrySet() {
		return extraParameters.entrySet();
	}

}
