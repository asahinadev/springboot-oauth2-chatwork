package com.example.spring.chatwork.dto.types;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import com.example.spring.chatwork.dto.Message;
import com.example.spring.chatwork.dto.Room;
import com.example.spring.chatwork.dto.Task;
import com.example.spring.chatwork.dto.Status;

public class TypeReference {

	public static final ParameterizedTypeReference<Status> STATUS = new ParameterizedTypeReference<Status>() {
	};

	public static final ParameterizedTypeReference<List<Room>> ROOMS = new ParameterizedTypeReference<List<Room>>() {
	};

	public static final ParameterizedTypeReference<Room> ROOM = new ParameterizedTypeReference<Room>() {
	};

	public static final ParameterizedTypeReference<List<Message>> MESSAGES = new ParameterizedTypeReference<List<Message>>() {
	};

	public static final ParameterizedTypeReference<Message> MESSAGE = new ParameterizedTypeReference<Message>() {
	};

	public static final ParameterizedTypeReference<List<Task>> TASKS = new ParameterizedTypeReference<List<Task>>() {
	};

	public static final ParameterizedTypeReference<Task> TASK = new ParameterizedTypeReference<Task>() {
	};

}
