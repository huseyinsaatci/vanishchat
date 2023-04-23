package com.shayn.vanishchat.exception;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketException extends RuntimeException {
  private final WebSocketSession session;
  protected final String message;

  public WebSocketException(WebSocketSession session, String message) {
    super(message);
    this.session = session;
    this.message = message;
  }

  public void sendExceptionMessage() throws IOException {
    session.sendMessage(new TextMessage(message));
  }

  public void closeConnection() throws IOException {
    if (!session.isOpen())
      return;
    session.close();
  }
}
