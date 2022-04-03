package com.natwest.publisher.controller;

import com.natwest.publisher.model.RestApiResponse;
import com.natwest.publisher.model.Transaction;
import com.natwest.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/*
created by Rahul sawaria on 03/04/22
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("publish/")
public class PublisherController {

    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private final PublisherService publisherService;

    @PostMapping("account-transaction")
    public RestApiResponse publishTransactionData(@RequestBody @Valid Transaction transaction) {
        logger.info("Inside publish transaction data at : {} for account_Number : {}", new Date(), transaction.getAccountNumber());
        return RestApiResponse.buildSuccess(publisherService.publishMessage(transaction));
    }


}
