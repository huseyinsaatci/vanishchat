package com.shayn.vanishchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayn.vanishchat.model.Message;
import com.shayn.vanishchat.repository.MessageRepository;

@Service
public class MessageService {
  @Autowired
  MessageRepository messageRepository;

  public void saveMessage(String content, String userName, String room) {
    messageRepository.save(new Message(content, userName, room));
  }

  public Iterable<Message> getMessagesInRoom(String room) {
    return messageRepository.findByRoom(room);
  }
}
