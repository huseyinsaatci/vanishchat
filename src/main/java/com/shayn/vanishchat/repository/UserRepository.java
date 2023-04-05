package com.shayn.vanishchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shayn.vanishchat.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
