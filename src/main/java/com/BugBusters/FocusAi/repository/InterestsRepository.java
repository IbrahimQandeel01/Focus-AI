package com.BugBusters.FocusAi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugBusters.FocusAi.model.Interests;

public interface InterestsRepository extends JpaRepository<Interests, Integer> {

	Optional<Interests> findByDescription(String description);
	
}
