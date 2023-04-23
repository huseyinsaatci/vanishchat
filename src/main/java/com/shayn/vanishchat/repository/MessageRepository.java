package com.shayn.vanishchat.repository;

import org.springframework.data.repository.CrudRepository;

import com.shayn.vanishchat.model.Message;

public interface MessageRepository extends CrudRepository<Message, String> {
  public Iterable<Message> findByRoom(String room);
}
