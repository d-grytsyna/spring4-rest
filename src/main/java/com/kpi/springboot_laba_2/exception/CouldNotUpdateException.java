package com.kpi.springboot_laba_2.exception;


public class CouldNotUpdateException extends RuntimeException{
    public CouldNotUpdateException(String message) {
        super(message);
    }
}
