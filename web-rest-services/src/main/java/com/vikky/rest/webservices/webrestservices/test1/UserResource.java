package com.vikky.rest.webservices.webrestservices.test1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDAO service;
	
	@Autowired
	private MessageSource messageSource;
	
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
	
	//@GetMapping("/users/{i}")
	public Resource<User> getUserHOETAOUS(@PathVariable int i) {
		User user = service.findOne(i);
		if(user == null) {
			throw new UserNotFoundException("user " + i + " not found");
		}
		
		Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		
		resource.add(link.withRel("all-users"));
		return resource;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> create(@Valid @RequestBody User u) {
		
		
		User user = service.add(u);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	//Internationalization
	@GetMapping("/users/message")
	public String getMessage(@RequestHeader(name="Accept-Language",required = false)Locale locale) {
		return messageSource.getMessage("internation.message",null, locale);
	}

}
