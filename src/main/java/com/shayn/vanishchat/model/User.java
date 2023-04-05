package com.shayn.vanishchat.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("user")
public class User {
  @Id
  private UUID sessionId;

  private String userName;
}
