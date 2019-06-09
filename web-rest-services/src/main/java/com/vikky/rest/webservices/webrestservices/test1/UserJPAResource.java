package com.vikky.rest.webservices.webrestservices.test1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserDAO service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/jpa/users")
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{i}")
	public Optional<User> getUser(@PathVariable int i) {
		Optional<User> user = userRepository.findById(i);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("user " + i + " not found");
		}
		

		return user;
	}
	
	
	@DeleteMapping("/jpa/users/{i}")
	public void deleteUser(@PathVariable int i) {
		userRepository.deleteById(i);
	}
	
	@GetMapping("/jpa/users/dynamic/{i}")
	public MappingJacksonValue getDynamicFilterUser(@PathVariable int i) {
		User user = service.findOne(i);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("birthDate");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeTestFilter", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(user);
		mapping.setFilters(filters);
		if(user == null) {
			throw new UserNotFoundException("user " + i + " not found");
		}
		

		return mapping;
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
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> create(@Valid @RequestBody User u) {
		
		
		User user = userRepository.save(u);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<User> createPosts(@PathVariable int id, @RequestBody Posts posts) {
		
		
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("user " + id + " not found");
		}
		User userObj = user.get();
		posts.setUser(userObj);
		postsRepository.save(posts);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(posts.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Posts> getPosts(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("user " + id + " not found");
		}
		
		
		

		return user.get().getPosts();
	}
	
	//Internationalization
	@GetMapping("/jpa/users/message")
	public String getMessage(@RequestHeader(name="Accept-Language",required = false)Locale locale) {
		return messageSource.getMessage("international.message",null, locale);
	}

}
