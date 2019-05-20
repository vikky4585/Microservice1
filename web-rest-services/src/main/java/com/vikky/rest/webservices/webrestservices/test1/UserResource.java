package com.vikky.rest.webservices.webrestservices.test1;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDAO service;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/users/{i}")
	public User getUser(@PathVariable int i) {
		User user = service.findOne(i);
		if(user == null) {
			throw new UserNotFoundException("user " + i + " not found");
		}
		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> create(@RequestBody User u) {
		
		
		User user = service.add(u);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}

}
