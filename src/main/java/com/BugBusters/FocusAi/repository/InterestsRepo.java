package com.BugBusters.FocusAi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugBusters.FocusAi.model.Interests;

public interface InterestsRepo extends JpaRepository<Interests, Integer> {
	
}
