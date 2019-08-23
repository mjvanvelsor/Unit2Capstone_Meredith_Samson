package com.trilogyed.retailapiservice.exception;

public class ServiceFailException extends RuntimeException {
   public ServiceFailException(){}
   public ServiceFailException(String message) {
      super(message);
   }
}
