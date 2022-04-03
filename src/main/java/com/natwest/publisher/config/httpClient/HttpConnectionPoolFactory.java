package com.natwest.publisher.config.httpClient;

import com.natwest.publisher.enums.RequestServer;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

/*
created by Rahul sawaria on 12/07/21
*/
@Component
@RequiredArgsConstructor
public class HttpConnectionPoolFactory {

    private final ListenerConnectionPool listenerConnectionPool;

    public CloseableHttpClient getConnectionPool(RequestServer server) {

        if (server == RequestServer.LISTENER) {
            return listenerConnectionPool.getConnection();
        }
        return null;

    }
}
