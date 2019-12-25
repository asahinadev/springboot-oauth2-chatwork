package com.example.spring.chatwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.chatwork.api.ChatworkApi;
import com.example.spring.chatwork.dto.Message;
import com.example.spring.chatwork.dto.Room;
import com.example.spring.chatwork.dto.params.ToMapType;
import com.example.spring.chatwork.dto.types.TypeReference;

@Controller
@RequestMapping("/rooms")
public class RoomsMessageController {

	public static String messageUrl(Long id1) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id1}/members")
				.build(id1).toASCIIString();
	}

	public static String messageUrl(Long id1, Long id2) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id1}/members/{id2}")
				.build(id1, id2).toASCIIString();
	}

	public static String messageUrl(Long id1, String type) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id1}/members/{type}")
				.build(id1, type).toASCIIString();
	}

	@Autowired
	ChatworkApi api;

	@GetMapping("{id}/messages")
	@ResponseBody
	public List<Message> index(@PathVariable("id") Long id) {
		return api.get(messageUrl(id), TypeReference.MESSAGES);
	}

	@PostMapping("{id}/messages")
	@ResponseBody
	public Message create(Message message, @PathVariable("id1") Long id1) {
		return api.post(messageUrl(id1), message.parameters(ToMapType.CREATE), TypeReference.MESSAGE);
	}

	@GetMapping("{id1}/messages/{id2}")
	@ResponseBody
	public Message get(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		return api.get(messageUrl(id1, id2), TypeReference.MESSAGE);
	}

	@PostMapping("{id1}/messages/{id2}")
	@ResponseBody
	public Message modify(
			Message message, @PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		return api.put(messageUrl(id1, id2), message.parameters(ToMapType.MODIFY), TypeReference.MESSAGE);
	}

	@DeleteMapping("{id1}/messages/{id2}")
	@ResponseBody
	public Message delete(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		return api.del(messageUrl(id1, id2), TypeReference.MESSAGE);
	}

	@PutMapping("{id1}/messages/read")
	@ResponseBody
	public Room read(Message message, @PathVariable("id1") Long id1) {
		return api.put(messageUrl(id1, "read"), message.parameters(ToMapType.STATUS), TypeReference.ROOM);
	}

	@PutMapping("{id}/messages/unread")
	@ResponseBody
	public Room unread(Message message, @PathVariable("id1") Long id1) {
		return api.put(messageUrl(id1, "unread"), message.parameters(ToMapType.STATUS), TypeReference.ROOM);
	}

}
