package com.capgemini.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String msg) {
        super(msg);
    }
}