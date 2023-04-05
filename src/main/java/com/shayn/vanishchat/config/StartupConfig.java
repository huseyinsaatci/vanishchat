package com.shayn.vanishchat.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartupConfig implements CommandLineRunner {

  private final SocketIOServer socketIOServer;

  @Override
  public void run(String... args) throws Exception {
    socketIOServer.start();
  }
}
