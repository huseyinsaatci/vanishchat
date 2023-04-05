package com.shayn.vanishchat.model;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("message")
public class Message {
  String content;
  String userName;
}