package com.natwest.publisher.service;

import com.natwest.publisher.model.Transaction;

/*
created by Rahul sawaria on 03/04/22
*/
public interface PublisherService {

    Object publishMessage(Transaction transaction);

}
