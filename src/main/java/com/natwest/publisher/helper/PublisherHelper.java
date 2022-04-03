package com.natwest.publisher.helper;

import com.natwest.publisher.exception.PublisherApiException;
import com.natwest.publisher.exception.PublisherBaseException;
import com.natwest.publisher.model.Transaction;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/*
created by Rahul sawaria on 03/04/22
*/
@Component
public class PublisherHelper {

    public void validateRequest(Transaction transaction) {

        if (String.valueOf(transaction.getAccountNumber()).length() < 11 || String.valueOf(transaction.getAccountFrom()).length() < 11 ||
                String.valueOf(transaction.getAccountNumber()).length() > 11 || String.valueOf(transaction.getAccountFrom()).length() > 11) {
            throw new PublisherBaseException(MessageFormat.format(PublisherApiException.INVALID_REQUEST.getMessage(),
                    "account Number"));
        }

        if (transaction.getAmount() <= 0)
            throw new PublisherBaseException(PublisherApiException.INVALID_AMOUNT.getMessage());

    }
}
