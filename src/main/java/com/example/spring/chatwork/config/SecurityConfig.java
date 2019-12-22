package com.example.spring.chatwork.config;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.CustomUserTypesOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.spring.chatwork.oauth.user.ChatworkUserService;
import com.example.spring.chatwork.oauth2.authorization.AuthorizationResponseClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig
		extends WebSecurityConfigurerAdapter {

	/* beans */
	@Bean
	public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {

		Map<String, Class<? extends OAuth2User>> customUserTypes = new LinkedHashMap<>();

		List<OAuth2UserService<OAuth2UserRequest, OAuth2User>> userServices = new LinkedList<>();
		userServices.add(new ChatworkUserService());
		if (!customUserTypes.isEmpty()) {
			userServices.add(new CustomUserTypesOAuth2UserService(customUserTypes));
		}
		userServices.add(new DefaultOAuth2UserService());
		return new DelegatingOAuth2UserService<>(userServices);
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		return new AuthorizationResponseClient();
	}

	public String[] permitAllList() {
		return Arrays.asList(
				"/error**",
				"/login**",
				"/webjars/**",
				"/oauth2/**",
				"/**/*.css",
				"/**/*.js",
				"/**/*.map",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.jpg",
				"/**/*.ttf",
				"/favicon.ico"

		/*END*/).toArray(new String[11]);
	}

	/* configure */

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 認証エリア設定
		configure(http.authorizeRequests());

		// BASIC 認証
		configure(http.httpBasic());

		// FORM 認証
		configure(http.formLogin());

		// LOGOUT 設定
		configure(http.logout());

		// CSRF
		configure(http.csrf());

		// CORS
		configure(http.cors());

		// OAuth v2 認証
		configure(http.oauth2Login());
	}

	/**
	 * 認証エリア設定,
	 * @param auth 設定
	 */
	protected void configure(
			ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry auth) {
		auth
				.antMatchers(permitAllList()).permitAll()
				.anyRequest().authenticated();

	}

	/**
	 * BASIC 認証,
	 * @param basic 設定
	 */
	protected void configure(HttpBasicConfigurer<HttpSecurity> basic) {
		basic.disable();
	}

	/**
	 * FORM 認証,
	 * @param form 設定
	 */
	protected void configure(FormLoginConfigurer<HttpSecurity> form) {
		form.disable();
	}

	/**
	 * LOGOUT 設定.
	 * @param logout 設定
	 */
	protected void configure(LogoutConfigurer<HttpSecurity> logout) {
		// logout.disable();
	}

	/**
	 * CSRF 対策
	 * @param csrf 設定
	 */
	protected void configure(CsrfConfigurer<HttpSecurity> csrf) {
		csrf.disable();
	}

	/**
	 * CORS 対策
	 * @param cors 設定
	 */
	protected void configure(CorsConfigurer<HttpSecurity> cors) {
		cors.disable();
	}

	/**
	 * OAUTH2 認証設定
	 * @param oauth 設定
	 */
	protected void configure(OAuth2LoginConfigurer<HttpSecurity> oauth) {

		oauth

				// 認証エンドポイント
				.authorizationEndpoint()
				// .authorizationRequestRepository(authorizationRequestRepository())
				.and()

				// リダイレクトエンドポイント
				.redirectionEndpoint()
				.and()

				// アクセストークンエンドポイント
				.tokenEndpoint()
				.accessTokenResponseClient(accessTokenResponseClient())
				.and()

				// ユーザー情報エンドポイント
				.userInfoEndpoint()
				.userService(oauth2UserService());
	}

}
