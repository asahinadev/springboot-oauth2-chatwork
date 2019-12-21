package com.example.spring.chatwork.config;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.spring.chatwork.oauth.user.ChatworkUserService;
import com.example.spring.oauth2.AuthorizationCodeTokenResponseClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig
		extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http)
			throws Exception {
		super.configure(http);

		http.authorizeRequests() //
				.antMatchers("/error**").permitAll() //
				.antMatchers("/login**").permitAll() //
				.antMatchers("/webjars/**").permitAll() //
				.antMatchers("/oauth2/**").permitAll() //
				.antMatchers("/**/*.css").permitAll() //
				.antMatchers("/**/*.js").permitAll() //
				.antMatchers("/**/*.map").permitAll() //
				.antMatchers("/**/*.png").permitAll() //
				.antMatchers("/**/*.gif").permitAll() //
				.antMatchers("/**/*.jpg").permitAll() //
				.antMatchers("/**/*.ttf").permitAll() //
				.antMatchers("/favicon.ico").permitAll() //
				.anyRequest().authenticated() //
				.and()

				.formLogin().disable() //
				.logout().disable()//
				.httpBasic().disable()//
				.csrf().disable()//

				// OAuth v2 認証
				.oauth2Login()

				// 認証エンドポイント
				.authorizationEndpoint().and()

				// リダイレクトエンドポイント
				.redirectionEndpoint().and()

				// アクセストークンエンドポイント
				.tokenEndpoint()
				.accessTokenResponseClient(new AuthorizationCodeTokenResponseClient())
				.and()

				// ユーザー情報エンドポイント
				.userInfoEndpoint()
				.userService(userService())
				.and();
	}

	OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {

		Map<String, Class<? extends OAuth2User>> customUserTypes = new LinkedHashMap<>();

		List<OAuth2UserService<OAuth2UserRequest, OAuth2User>> userServices = new LinkedList<>();
		userServices.add(new ChatworkUserService());
		if (!customUserTypes.isEmpty()) {
			userServices.add(new CustomUserTypesOAuth2UserService(customUserTypes));
		}
		userServices.add(new DefaultOAuth2UserService());
		return new DelegatingOAuth2UserService<>(userServices);
	}

}
