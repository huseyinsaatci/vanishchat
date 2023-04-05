package com.shayn.vanishchat.socket;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.shayn.vanishchat.dto.MessageDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SocketModule {

  private final SocketIOServer socketIOServer;

  private static final String sendMessageEvent = "send_message";
  private static final String getMessageEvent = "get_message";
  private static final String roomParam = "room";
  private static final String userNameParam = "user_name";
  private static final String defaultRoom = "default";

  public SocketModule(SocketIOServer socketIOServer) {
    this.socketIOServer = socketIOServer;
    socketIOServer.addConnectListener(onConnected());
    socketIOServer.addDisconnectListener(onDisconnected());
    socketIOServer.addEventListener(sendMessageEvent, MessageDto.class, onMessageReceived());
  }

  private DataListener<MessageDto> onMessageReceived() {
    return (senderClient, data, ackSender) -> {
      log.info(String.format("%s -> %s", senderClient.getSessionId(), data.getContent()));
      String room = senderClient.getHandshakeData().getSingleUrlParam(roomParam);
      broadcastEventExceptSelf(senderClient, room, getMessageEvent, data.getContent());
    };
  }

  private ConnectListener onConnected() {
    return client -> {
      String room = client.getHandshakeData().getSingleUrlParam(roomParam);
      room = room == null ? defaultRoom : room;
      String userName = client.getHandshakeData().getSingleUrlParam(userNameParam);
      log.info(String.format("Username: %s", userName));
      client.joinRoom(room);
      broadcastEventExceptSelf(client, room, getMessageEvent,
          String.format("%s connected to -> %s",
              client.getSessionId(), room));
      log.info(String.format("SocketID: %s connected", client.getSessionId().toString()));
    };
  }

  private DisconnectListener onDisconnected() {
    return client -> {
      String room = client.getHandshakeData().getSingleUrlParam(roomParam);
      room = room == null ? defaultRoom : room;
      broadcastEventExceptSelf(client, room, getMessageEvent,
          String.format("%s disconnected from -> %s",
              client.getSessionId(), room));
      log.info(String.format("SocketID: %s disconnected!", client.getSessionId().toString()));
    };
  }

  private void broadcastEventExceptSelf(SocketIOClient client, String room, String eventName, String message) {
    client.getNamespace().getRoomOperations(room).getClients().forEach(
        x -> {
          if (!x.getSessionId().equals(client.getSessionId())) {
            x.sendEvent(eventName, message);
          }
        });
  }
}