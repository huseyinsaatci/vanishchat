package com.shayn.vanishchat.exception;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(WebSocketException.class)
  protected void handleWebSocketException(WebSocketException ex) throws IOException {
    ex.sendExceptionMessage();
    ex.closeConnection();
    log.warn(ex.getMessage());
  }
}
