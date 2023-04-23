package com.shayn.vanishchat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RedisHash("user")
public class User {
  @Id
  private String sessionId;

  @Indexed
  private String userName;
}
