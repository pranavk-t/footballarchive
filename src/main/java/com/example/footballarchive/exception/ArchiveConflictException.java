package com.example.footballarchive.exception;

public class ArchiveConflictException extends RuntimeException{
    public ArchiveConflictException(String message) {
        super(message);
    }
}
