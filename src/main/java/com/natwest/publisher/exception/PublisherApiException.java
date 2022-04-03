package com.natwest.publisher.exception;

import lombok.Getter;

/*
created by Rahul sawaria on 03/04/22
*/
public enum PublisherApiException {
    SOMETHING_WENT_WRONG("Seems like something went wrong"),
    INVALID_REQUEST("{0} length should be of 11 digit"),
    INVALID_AMOUNT("Enter valid amount value");

    @Getter
    private final String message;

    PublisherApiException(String message) {
        this.message = message;
    }
}
