package com.shayn.vanishchat.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.shayn.vanishchat.exception.UserAlreadyExistsException;
import com.shayn.vanishchat.exception.UserNameNotFoundException;
import com.shayn.vanishchat.service.MessageService;
import com.shayn.vanishchat.service.UserService;
import com.shayn.vanishchat.util.WebSocketUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

  @Autowired
  private UserService userService;
  @Autowired
  private MessageService messageService;
  @Autowired
  private WebSocketUtils webSocketUtils;

  private final ConcurrentHashMap<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

  private static final String DEFAULT_ROOM = "defaultRoom";

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String query = session.getUri().getQuery();
    String room = webSocketUtils.extractRoom(query);
    room = room == null ? DEFAULT_ROOM : room;
    String userName = webSocketUtils.extractUserName(query);
    String sessionId = session.getId();
    if (userName == null) {
      return;
      // throw new UserNameNotFoundException(session, "Username is required!");
    }
    if (userService.userExist(userName)) {
      return;
      // throw new UserAlreadyExistsException(session, "Username already exists!");
    }
    userService.createUser(sessionId, userName);
    sessions.put(session, room);
    broadcastMesssage(session, room, String.format("[%s] connected.", userName));
    log.info(String.format("SocketID: %s connected.", sessionId));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
    String query = session.getUri().getQuery();
    String room = webSocketUtils.extractRoom(query);
    room = room == null ? DEFAULT_ROOM : room;
    String userName = webSocketUtils.extractUserName(query);
    String sessionId = session.getId();
    userService.deleteUser(sessionId);
    sessions.remove(session);
    broadcastMesssage(session, room, String.format("[%s] disconnected.", userName));
    log.info(String.format("SocketID: %s disconnected.", sessionId));
  }

  // @Scheduled(fixedRate = 10000)
  // void sendPeriodicMessages() throws IOException {
  // for (WebSocketSession session : sessions) {
  // if (session.isOpen()) {
  // String broadcast = "server periodic message " + LocalTime.now();
  // log.info("Server sends: {}", broadcast);
  // session.sendMessage(new TextMessage(broadcast));
  // }
  // }
  // }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String query = session.getUri().getQuery();
    String room = webSocketUtils.extractRoom(query);
    room = room == null ? DEFAULT_ROOM : room;
    String userName = webSocketUtils.extractUserName(query);
    String sessionId = session.getId();
    String userMessage = message.getPayload();
    messageService.saveMessage(userMessage, userName, room);
    broadcastMesssage(session, room, String.format("[%s]: %s.", userName, userMessage));
    log.info(String.format("[%s]: %s.", sessionId, userMessage));
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    log.info("Server transport error: {}", exception.getMessage());
  }

  @Override
  public List<String> getSubProtocols() {
    return Collections.singletonList("subprotocol.demo.websocket");
  }

  private void broadcastMesssage(WebSocketSession client, String room,
      String message) throws IOException {
    for (WebSocketSession session : sessions.keySet()) {
      if (session.isOpen() && !session.equals(client) && sessions.get(session).equals(room)) {
        session.sendMessage(new TextMessage(message));
      }
    }
  }
}
