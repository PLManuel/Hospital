package com.hospital.office_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  
  private final HttpStatus status;
  private final String message;

  public CustomException(String message, HttpStatus status) {
    super(message);
    this.message = message;
    this.status = status;
  }

  public CustomException(String message) {
    this(message, HttpStatus.BAD_REQUEST);
  }
}
