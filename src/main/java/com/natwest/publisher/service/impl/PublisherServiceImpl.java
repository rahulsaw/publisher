package com.natwest.publisher.service.impl;

import com.natwest.publisher.config.httpClient.HttpClientService;
import com.natwest.publisher.config.httpClient.ListenerClientConfig;
import com.natwest.publisher.enums.RequestServer;
import com.natwest.publisher.exception.PublisherApiException;
import com.natwest.publisher.exception.PublisherBaseException;
import com.natwest.publisher.helper.PublisherHelper;
import com.natwest.publisher.model.RestApiResponse;
import com.natwest.publisher.model.Transaction;
import com.natwest.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.Base64;
import java.util.Objects;

/*
created by Rahul sawaria on 03/04/22
*/
@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private static final Logger logger = LoggerFactory.getLogger(PublisherServiceImpl.class);

    private final HttpClientService httpClientService;
    private final PublisherHelper publisherHelper;
    private final ListenerClientConfig listenerClientConfig;

    @Override
    public Object publishMessage(Transaction transaction) {

        publisherHelper.validateRequest(transaction);

        RestApiResponse response = httpClientService.postResponse(listenerClientConfig.getListenerDomainUrl() + listenerClientConfig.getInsertDataUrl(),
                null, Base64.getEncoder().encodeToString(SerializationUtils.serialize(transaction)),
                RestApiResponse.class, RequestServer.LISTENER);

        if (response == null || !response.isSuccess() || Objects.isNull(response.getData()))
            throw new PublisherBaseException(PublisherApiException.SOMETHING_WENT_WRONG.getMessage());
        logger.info("Transaction published successfully for account number {}", transaction.getAccountNumber());

        return "transaction inserted successfully";
    }

}
