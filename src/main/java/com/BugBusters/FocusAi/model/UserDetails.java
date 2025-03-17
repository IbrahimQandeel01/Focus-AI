package com.BugBusters.FocusAi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity(name = "User_table")
public class UserDetails {
	@Id
	@GeneratedValue
    private Integer id ;
	@Size(min = 3,message = "must have at least 3 charachter")
	@Nonnull
	private String name;
	@Email
	@Nonnull
	private String email;
	
	@ManyToMany
    @JoinTable(
        name = "user_interests",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
	@JsonIgnore
    private Set<Interests> interests = new HashSet<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Interests> getInterests() {
		return interests;
	}
	public void setInterests(Set<Interests> interests) {
		this.interests = interests;
	}
	
	
	
}
