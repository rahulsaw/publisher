package com.natwest.publisher.config.httpClient;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
created by Rahul sawaria on 03/04/22
*/
@Component
@Data
public class ListenerClientConfig {

    @Value("${listener.domain.url:localhost:8081/listener/}")
    private String listenerDomainUrl;

    @Value("${receive.data.url:insert-data}")
    private String insertDataUrl;
}
