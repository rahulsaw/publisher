package com.natwest.publisher.exception;

import com.natwest.publisher.controller.PublisherController;
import com.natwest.publisher.model.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

import static com.natwest.publisher.exception.PublisherApiException.SOMETHING_WENT_WRONG;

/*
created by Rahul sawaria on 03/04/22
*/
@ControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected RestApiResponse invalidRequestExceptionHandler(MethodArgumentNotValidException exception) {
        String message = getErrorMessage(exception.getBindingResult());
        return RestApiResponse.buildFail(HttpStatus.BAD_REQUEST.toString(), message);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PublisherBaseException.class)
    @ResponseBody
    protected RestApiResponse publisherExceptionHandler(PublisherBaseException exception) {
        logger.error("Exception name : {} and message is : {}", exception.getName(), exception.getMessage());
        return RestApiResponse.buildFail(exception.getName(), exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected RestApiResponse genericExceptionHandler(Exception exception, HttpServletRequest httpServletRequest) {
        return RestApiResponse.buildFail(RestApiResponse.MessageApiResponse.build(SOMETHING_WENT_WRONG.name(), SOMETHING_WENT_WRONG.getMessage()));
    }

    private String getErrorMessage(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        return fieldError.getField() + " " + fieldError.getDefaultMessage();
    }
}
