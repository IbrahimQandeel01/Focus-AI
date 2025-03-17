package com.BugBusters.FocusAi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BugBusters.FocusAi.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {
}
