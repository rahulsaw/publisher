package com.natwest.publisher.exception;

import lombok.Getter;

/*
created by Rahul sawaria on 03/04/22
*/
@Getter
public class PublisherBaseException extends RuntimeException {

    private String name;
    private final String message;

    public PublisherBaseException(String message) {
        super();
        this.message = message;
    }

    public PublisherBaseException(String name, String message) {
        super();
        this.name = name;
        this.message = message;
    }
}
