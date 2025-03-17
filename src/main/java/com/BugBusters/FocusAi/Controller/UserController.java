package com.BugBusters.FocusAi.Controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.BugBusters.FocusAi.exception.UserNotFoundExeption;
import com.BugBusters.FocusAi.model.Interests;
import com.BugBusters.FocusAi.model.UserDetails;
import com.BugBusters.FocusAi.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserController {

	private UserRepository repository;

	public UserController(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping("/users")
	public List<UserDetails> retrieveAllUsers() {
		return repository.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<UserDetails> retrieveOneUserById(@PathVariable Integer id) {

		Optional<UserDetails> oneById = repository.findById(id);

		if (oneById == null) {
			throw new UserNotFoundExeption("id : " + id);
		}

		EntityModel<UserDetails> entityModel = EntityModel.of(oneById.get());

		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass())
				.retrieveAllUsers());
				entityModel.add(link.withRel("all-users"));

		return entityModel;
	}

	
	@PostMapping("/users")
	public ResponseEntity<Object> CreateUsers(@Valid @RequestBody UserDetails user) {

		UserDetails savedUser = repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	  
	 
	@DeleteMapping("/users/{id}/delete")
	public void DeleteUserById(@PathVariable Integer id) {
		repository.deleteById(id);
	}

	@GetMapping("/users/{id}/interests")
	public Set<Interests> retrieveAllInterestsForUser(@PathVariable Integer id) {
		Optional<UserDetails> user = repository.findById(id);

		if (user.isEmpty()) {
			throw new UserNotFoundExeption("id: " + id);
		}

		return user.get().getInterests() == null ? new HashSet<>() 
				: user.get().getInterests();
	}

}
