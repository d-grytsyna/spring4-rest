package com.kpi.springboot_laba_2.exception;

public class CouldNotDeleteException extends RuntimeException{
    public CouldNotDeleteException(String message) {
        super(message);
    }
}
