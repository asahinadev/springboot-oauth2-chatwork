package com.example.spring.chatwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring.chatwork.api.ChatworkApi;
import com.example.spring.chatwork.dto.Status;
import com.example.spring.chatwork.dto.Task;
import com.example.spring.chatwork.dto.User;
import com.example.spring.chatwork.dto.params.ToMapType;
import com.example.spring.chatwork.dto.types.TypeReference;

@Controller
@RequestMapping("/my")
public class MyController {

	@Autowired
	ChatworkApi chatworkApi;

	@GetMapping("status")
	@ResponseBody
	public Status status() {

		return chatworkApi.get("https://api.chatwork.com/v2/my/status",
				TypeReference.STATUS);

	}

	@GetMapping("tasks")
	@ResponseBody
	public List<Task> tasks(@AuthenticationPrincipal User user, Task task) {

		task.setAssignedByAccount(user);
		return chatworkApi.get("https://api.chatwork.com/v2/my/tasks", task.parameters(ToMapType.LIST),
				TypeReference.TASKS);

	}

}
