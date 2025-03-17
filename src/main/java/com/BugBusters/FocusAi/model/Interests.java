package com.BugBusters.FocusAi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name = "Interests")
public class Interests {
	@Id
	@GeneratedValue
	private Integer id;

	private String description;

	@ManyToMany(mappedBy = "interests")
	@JsonIgnore
	private Set<UserDetails> users = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserDetails> getUsers() {
		return users;
	}

	public void setUsers(Set<UserDetails> users) {
		this.users = users;
	}
	
	
}
