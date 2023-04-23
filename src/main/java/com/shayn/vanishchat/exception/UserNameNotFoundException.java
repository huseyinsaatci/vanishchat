package com.shayn.vanishchat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.socket.WebSocketSession;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Username is required!")
public class UserNameNotFoundException extends WebSocketException {

  public UserNameNotFoundException(WebSocketSession session, String message) {
    super(session, message);
  }
}
