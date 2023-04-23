package com.shayn.vanishchat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash(value = "message", timeToLive = 15)
public class Message {
  @Id
  private String id;
  private final String content;
  private final String userName;
  @Indexed
  private final String room;

  @Override
  public String toString() {
    return String.format("[%s]: %s", userName, content);
  }
}