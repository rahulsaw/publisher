package com.natwest.publisher.config.httpClient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/*
created by Rahul sawaria on 28/09/21
*/
@Component
public class ListenerConnectionPool {

    @Value("${listener.http.connection.socketTimeout:10}")
    private String socketTimeout;

    private CloseableHttpClient httpClient;


    @PostConstruct
    private void setupConnectionManager() {

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(Integer.parseInt(socketTimeout))
                .build();

        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }


    public CloseableHttpClient getConnection() {
        return httpClient;
    }

}
