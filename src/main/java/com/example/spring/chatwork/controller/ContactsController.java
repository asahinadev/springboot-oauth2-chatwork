package com.example.spring.chatwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.chatwork.api.ChatworkApi;
import com.example.spring.chatwork.dto.ChatworkUser;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

	@Autowired
	ChatworkApi chatworkApi;

	@GetMapping("")
	@ResponseBody
	public List<ChatworkUser> contacts() {

		return chatworkApi.get("https://api.chatwork.com/v2/contacts",
				new ParameterizedTypeReference<List<ChatworkUser>>() {
					// no process
				});
	}

}
