package com.app.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.springboot.entity.User;
import com.app.springboot.exception.ResourceNotFoundException;
import com.app.springboot.repository.UserRepository;

import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found " + userId));
	}

	@PostMapping
	public User createUser(@Valid @RequestBody User user) {
		return this.userRepository.save(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("id") long userId) {
		User getting = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found " + userId));

		getting.setFirstName(user.getFirstName());
		getting.setLastName(user.getLastName());
		getting.setEmail(user.getEmail());

		return this.userRepository.save(getting);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") long userId) {

		User getting = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found " + userId));

		this.userRepository.delete(getting);

		return ResponseEntity.ok().build();
	}

}
