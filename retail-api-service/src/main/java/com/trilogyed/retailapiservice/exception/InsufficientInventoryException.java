package com.trilogyed.retailapiservice.exception;

public class InsufficientInventoryException extends RuntimeException {
   public InsufficientInventoryException(){};
   public InsufficientInventoryException(String errorMsg) {
      super(errorMsg);
   }
}
