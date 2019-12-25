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
import com.example.spring.chatwork.dto.Room;
import com.example.spring.chatwork.dto.params.ToMapType;
import com.example.spring.chatwork.dto.types.TypeReference;

@Controller
@RequestMapping("/rooms")
public class RoomsController {

	public static String roomUrl() {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/")
				.build().toUriString();
	}

	public static String roomUrl(Long id) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id}/members")
				.build(id).toASCIIString();
	}

	@Autowired
	ChatworkApi api;

	@GetMapping("")
	@ResponseBody
	public List<Room> index() {
		return api.get(roomUrl(), TypeReference.ROOMS);
	}

	@PostMapping("")
	@ResponseBody
	public Room create(Room room) {
		return api.post(roomUrl(), room.parameters(ToMapType.CREATE), TypeReference.ROOM);
	}

	@GetMapping("{id}")
	@ResponseBody
	public Room get(@PathVariable("id") Long id) {
		return api.get(roomUrl(id), TypeReference.ROOM);
	}

	@PostMapping("{id}")
	@ResponseBody
	public Room modify(Room room, @PathVariable("id") Long id) {
		return api.put(roomUrl(id), room.parameters(ToMapType.MODIFY), TypeReference.ROOM);
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Room delete(Room room, @PathVariable("id") Long id) {
		return api.del(roomUrl(id), room.parameters(ToMapType.DELETE), TypeReference.ROOM);
	}

}
