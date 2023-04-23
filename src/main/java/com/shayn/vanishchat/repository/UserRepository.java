package com.shayn.vanishchat.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shayn.vanishchat.model.User;

public interface UserRepository extends CrudRepository<User, String> {
  Optional<User> findByUserName(String userName);
}
