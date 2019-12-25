package com.example.spring.chatwork.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring.chatwork.dto.ChatworkUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String index(
			Model model,
			@AuthenticationPrincipal ChatworkUser user) {

		log.debug("user :{}", user);
		model.addAttribute("user", user);

		return "index";
	}
}
