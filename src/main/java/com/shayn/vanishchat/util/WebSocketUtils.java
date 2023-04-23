package com.shayn.vanishchat.util;

import org.springframework.stereotype.Component;

@Component
public class WebSocketUtils {
  public String extractParam(String uri, String paramName) {
    if (uri == null)
      return null;
    String[] params = uri.split("&");
    for (String param : params) {
      String[] keyValue = param.split("=");
      if (keyValue.length == 2 && keyValue[0].equals(paramName)) {
        return keyValue[1];
      }
    }
    return null;
  }

  public String extractRoom(String uri) {
    return extractParam(uri, "room");
  }

  public String extractUserName(String uri) {
    return extractParam(uri, "user_name");
  }
}
