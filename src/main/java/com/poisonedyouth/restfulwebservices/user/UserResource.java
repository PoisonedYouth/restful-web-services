package com.poisonedyouth.restfulwebservices.user;

import org.springframework.context.MessageSource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserResource {

	private UserDaoService userDaoService;
	private MessageSource messageSource;

	public UserResource(UserDaoService userDaoService, MessageSource messageSource) {
		this.userDaoService = userDaoService;
		this.messageSource = messageSource;
	}

	//retrieveAllUsers
	@GetMapping("")
	public ResponseEntity<Resources<User>> retrieveAllUsers() {
		final List<User> userList = userDaoService.findAll();
		final Resources<User> resources = new Resources<>(userList);
		final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
		resources.add(new Link(uriString, "self"));
		return ResponseEntity.ok(resources);
	}

	//retrieveUser(int id)
	@GetMapping("{id}")
	public ResponseEntity<Resource<User>> retrieveUser(@PathVariable int id) {
		User user = userDaoService.findById(id);
		if (user == null) {
			throw new UserNotFoundException("id=" + id);
		}
		Resource<User> userResource = new Resource<>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		userResource.add(linkTo.withRel("all-users"));

		return ResponseEntity.ok(userResource);
	}

	// create user
	@PostMapping("")
	public ResponseEntity<Resource<User>> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.save(user);
		Resource<User> userResource = new Resource<>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveUser(savedUser.getId()));
		userResource.add(linkTo.withRel("retrieve user"));
		final URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(userResource);
	}

	// delete user
	@DeleteMapping("{id}")
	public ResponseEntity<Resource<User>> deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteById(id);

		if (user == null) {
			throw new UserNotFoundException("id=" + id);
		}
		Resource<User> userResource = new Resource<>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		userResource.add(linkTo.withRel("all-users"));
		return ResponseEntity.ok().body(userResource);
	}

	@GetMapping("localization-message")
	public String getLocalizationMessage(@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}
}
