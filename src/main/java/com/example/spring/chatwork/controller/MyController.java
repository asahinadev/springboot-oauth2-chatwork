package com.example.spring.chatwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.chatwork.api.ChatworkApi;
import com.example.spring.chatwork.dto.ChatworkUser;
import com.example.spring.chatwork.dto.MyStatus;
import com.example.spring.chatwork.dto.MyTasks;

@Controller
@RequestMapping("/my")
public class MyController {

	@Autowired
	ChatworkApi chatworkApi;

	@GetMapping("status")
	@ResponseBody
	public MyStatus status() {

		return chatworkApi.get("https://api.chatwork.com/v2/my/status",
				new ParameterizedTypeReference<MyStatus>() {
					// no process
				});

	}

	@GetMapping("tasks")
	@ResponseBody
	public List<MyTasks> tasks(
			@AuthenticationPrincipal ChatworkUser user,
			@RequestParam(required = false) List<String> status) {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

		parameters.add("assigned_by_account_id", "" + user.getAccountId());
		if (status != null) {
			parameters.put("status", status);
		}

		return chatworkApi.get("https://api.chatwork.com/v2/my/tasks", parameters,
				new ParameterizedTypeReference<List<MyTasks>>() {
					// no process
				});

	}

}
