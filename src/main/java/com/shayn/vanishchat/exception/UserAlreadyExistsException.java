package com.shayn.vanishchat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.socket.WebSocketSession;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists!")
public class UserAlreadyExistsException extends WebSocketException {
  public UserAlreadyExistsException(WebSocketSession session, String message) {
    super(session, message);
  }
}
