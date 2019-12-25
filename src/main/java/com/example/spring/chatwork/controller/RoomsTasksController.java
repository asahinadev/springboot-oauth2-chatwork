package com.example.spring.chatwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.chatwork.api.ChatworkApi;
import com.example.spring.chatwork.dto.Message;
import com.example.spring.chatwork.dto.Task;
import com.example.spring.chatwork.dto.params.ToMapType;
import com.example.spring.chatwork.dto.types.TypeReference;

@Controller
@RequestMapping("/rooms")
public class RoomsTasksController {

	public static String tasksUrl(Long id1) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id1}/tasks")
				.build(id1).toASCIIString();
	}

	public static String tasksUrl(Long id1, Long id2) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id1}/tasks/{id2}")
				.build(id1, id2).toASCIIString();
	}

	public static String tasksUrl(Long id1, Long id2, String type) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id1}/tasks/{id2}/{type}")
				.build(id1, id2, type).toASCIIString();
	}

	@Autowired
	ChatworkApi chatworkApi;

	@GetMapping("{id}/tasks")
	@ResponseBody
	public List<Task> index(@PathVariable("id") Long id) {
		return chatworkApi.get(tasksUrl(id), TypeReference.TASKS);
	}

	@PostMapping("{id}/tasks")
	@ResponseBody
	public Task create(Task message, @PathVariable("id1") Long id1) {
		return chatworkApi.post(tasksUrl(id1), message.parameters(ToMapType.CREATE), TypeReference.TASK);
	}

	@GetMapping("{id1}/tasks/{id2}")
	@ResponseBody
	public Task get(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		return chatworkApi.get(tasksUrl(id1, id2), TypeReference.TASK);
	}

	@PostMapping("{id1}/tasks/{id2}")
	@ResponseBody
	public Task modify(
			Message message, @PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		return chatworkApi.post(tasksUrl(id1, id2), message.parameters(ToMapType.MODIFY), TypeReference.TASK);
	}

	@DeleteMapping("{id1}/tasks/{id2}")
	@ResponseBody
	public Task complete(
			Message message, @PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		return chatworkApi.put(tasksUrl(id1, id2, "status"), message.parameters(ToMapType.STATUS), TypeReference.TASK);
	}
}
