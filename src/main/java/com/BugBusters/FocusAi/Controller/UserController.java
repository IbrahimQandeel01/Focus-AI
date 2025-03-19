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
import com.BugBusters.FocusAi.repository.InterestsRepository;
import com.BugBusters.FocusAi.repository.UserRepository;

import jakarta.validation.Valid;


@RestController
public class UserController {

	private UserRepository UsersRepository;
	private InterestsRepository interestsRepository;

	public UserController(UserRepository repository,InterestsRepository interestsRepository) {
		super();
		this.UsersRepository = repository;
		this.interestsRepository= interestsRepository;
	}

	@GetMapping("/users")
	public List<UserDetails> retrieveAllUsers() {
		return UsersRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<UserDetails> retrieveOneUserById(@PathVariable Integer id) {

		Optional<UserDetails> oneById = UsersRepository.findById(id);

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

		UserDetails savedUser = UsersRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	  
	 
	@DeleteMapping("/users/{id}/delete")
	public void DeleteUserById(@PathVariable Integer id) {
		UsersRepository.deleteById(id);
	}

	@GetMapping("/users/{id}/interests")
	public Set<Interests> retrieveAllInterestsForUser(@PathVariable Integer id) {
		Optional<UserDetails> user = UsersRepository.findById(id);

		if (user.isEmpty()) {
			throw new UserNotFoundExeption("id: " + id);
		}

		return user.get().getInterests() == null ? new HashSet<>() 
				: user.get().getInterests();
	}
	
	@PostMapping("/users/{id}/interests")
	public ResponseEntity<Object> createInterestsForUser(@PathVariable Integer id, @Valid @RequestBody Interests interest) {
	    Optional<UserDetails> userOpt = UsersRepository.findById(id);

	    if (userOpt.isEmpty()) {
	        throw new UserNotFoundExeption("id: " + id);
	    }

	    UserDetails user = userOpt.get();

	    Optional<Interests> existingInterest = interestsRepository.findByDescription(interest.getDescription());

	    Interests interestToSave;
	    if (existingInterest.isPresent()) {
	        interestToSave = existingInterest.get();
	    } else {
	        interestToSave = interestsRepository.save(interest);
	    }
	    
	    user.getInterests().add(interestToSave);
	    UsersRepository.save(user);

	    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                    .path("/{id}")
	                    .buildAndExpand(interestToSave.getId())
	                    .toUri();

	    return ResponseEntity.created(location).build();
	}



}
