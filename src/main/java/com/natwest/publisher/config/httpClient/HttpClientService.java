package com.natwest.publisher.config.httpClient;

import com.google.gson.Gson;
import com.natwest.publisher.Constant;
import com.natwest.publisher.enums.RequestServer;
import com.natwest.publisher.exception.PublisherBaseException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;


/*
created by Rahul sawaria on 12/07/21
*/
@Component
@RequiredArgsConstructor
public class HttpClientService {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientService.class);

    private final HttpConnectionPoolFactory httpConnectionPoolFactory;

    private static final Gson gson = new Gson();


    public <T> T getResponse(String requestUrl, Map<String, String> header, Class<T> responseClass, RequestServer server) {
        logger.info("Going to hit Request url : {}", requestUrl);
        CloseableHttpClient httpClient = httpConnectionPoolFactory.getConnectionPool(server);
        HttpGet httpGet = new HttpGet(requestUrl);
        if (!CollectionUtils.isEmpty(header))
            setHeaders(httpGet, header);

        CloseableHttpResponse httpResponse = null;
        try {
            long startTime = System.currentTimeMillis();
            httpResponse = httpClient.execute(httpGet);
            logger.info("GET - Time taken to received response for request url : {} is {} ms", requestUrl, System.currentTimeMillis() - startTime);
            return convertHttpResponse(requestUrl, httpResponse, responseClass);
        } catch (Exception e) {
            logger.error("Something went wrong in GET http request is : {}", e.getMessage() != null ? e.getMessage() : e);
            throw new PublisherBaseException(e.getMessage());
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (Exception e) {
                logger.error("Error in closing response ", e);
            }
        }
    }


    public <V, T> T postResponse(String requestUrl, Map<String, String> header, V request, Class<T> responseClass, RequestServer server) {
        logger.info("Going to hit Request url : {} with request body : {}", requestUrl, gson.toJson(request));
        CloseableHttpClient httpClient = httpConnectionPoolFactory.getConnectionPool(server);
        HttpPost httpPost = new HttpPost(requestUrl);
        if (!CollectionUtils.isEmpty(header))
            setHeaders(httpPost, header);


        httpPost.setHeader(Constant.CONTENT_TYPE_KEY, ContentType.APPLICATION_JSON.toString());
        CloseableHttpResponse httpResponse = null;
        try {
            httpPost.setEntity(new StringEntity(gson.toJson(request), ContentType.APPLICATION_JSON));
            long startTime = System.currentTimeMillis();
            httpResponse = httpClient.execute(httpPost);
            logger.info("Post - Time taken to received response for request url : {} is {} ms", requestUrl, System.currentTimeMillis() - startTime);
            return convertHttpResponse(requestUrl, httpResponse, responseClass);
        } catch (Exception e) {
            logger.error("Something went wrong in POST http request is : {}", e.getMessage() != null ? e.getMessage() : e);
            logger.error(String.valueOf(e));
            throw new PublisherBaseException(e.getMessage());
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (Exception e) {
                logger.error("Error in closing response ", e);
            }
        }
    }


    private <T> T convertHttpResponse(String requestUrl, HttpResponse httpResponse, Class<T> responseClass) {
        HttpEntity httpEntity = httpResponse.getEntity();

        if (httpEntity != null) {
            String responseString;
            int statusCode;
            try {
                statusCode = httpResponse.getStatusLine().getStatusCode();
                logger.info("Http status code is : {}", statusCode);
                responseString = EntityUtils.toString(httpEntity, "UTF-8");
                logger.info("Response for request url : {} , is : {}", requestUrl, responseString);
            } catch (IOException e) {
                logger.error("Something went wrong while converting http response is : {}", e.getMessage());
                throw new PublisherBaseException(e.getMessage());
            }

            if (responseClass != null & ((statusCode == HttpStatus.SC_OK) || (statusCode >= HttpStatus.SC_BAD_REQUEST && statusCode <= HttpStatus.SC_FORBIDDEN)))
                return gson.fromJson(responseString, responseClass);
        }

        if (responseClass != null)
            logger.error("http entity is null, returning null response.");

        return null;
    }


    private void setHeaders(HttpRequestBase http, Map<String, String> header) {
        for (Map.Entry<String, String> entry : header.entrySet())
            http.setHeader(entry.getKey(), entry.getValue());
    }


}
