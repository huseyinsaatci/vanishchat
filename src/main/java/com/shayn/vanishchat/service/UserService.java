package com.shayn.vanishchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayn.vanishchat.model.User;
import com.shayn.vanishchat.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  private String userName;

  public boolean userExist(String userName) {
    return userRepository.findByUserName(userName).isPresent();
  }

  public String getUserName(String sessionId) {
    userRepository.findById(sessionId).ifPresentOrElse(user -> {
      userName = user.getUserName();
    }, () -> userName = null);
    return userName;
  }

  public void createUser(String sessionId, String userName) {
    userRepository.save(new User(sessionId, userName));
  }

  public void deleteUser(String sessionId) {
    userRepository.deleteById(sessionId);
  }
}
