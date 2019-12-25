package com.example.spring.chatwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class RoomsMemberController {

	public static String membersUrl(Long id) {
		return UriComponentsBuilder.fromUriString("https://api.chatwork.com/v2/rooms/{id}/members")
				.build(id).toASCIIString();
	}

	@Autowired
	ChatworkApi api;

	@GetMapping("{id}/members")
	@ResponseBody
	public Room index(@PathVariable("id") Long id) {
		return api.get(membersUrl(id), TypeReference.ROOM);
	}

	@PostMapping("{id}/members")
	@ResponseBody
	public Room modify(Room room, @PathVariable("id") Long id) {
		return api.put(membersUrl(id), room.parameters(ToMapType.MEMBERS), TypeReference.ROOM);
	}

}
