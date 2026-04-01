package client;

import config.Config;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import report.ExtentLogger;

import org.apache.http.params.CoreConnectionPNames;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiClient {

    private static final int DEFAULT_RETRIES = 2;

    public static Response get(String endpoint) {
        return get(endpoint, DEFAULT_RETRIES);
    }

    public static Response get(String endpoint, int retries) {
        int attempt = 0;
        long backoff = 500; // ms
        while (true) {
            try {
                RestAssuredConfig config = RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, Config.getTimeoutMillis())
                                .setParam(CoreConnectionPNames.SO_TIMEOUT, Config.getTimeoutMillis())
                        );

                String base = Config.getBaseUrl();
                String fullUrl = base.endsWith("/") ? base.substring(0, base.length()-1) + endpoint : base + endpoint;

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", ContentType.JSON.toString());

                // Log request to Extent (include method, full URL, headers)
                ExtentLogger.logRequest("GET", fullUrl, null, headers);

                Response res = given()
                        .baseUri(Config.getBaseUrl())
                        .contentType(ContentType.JSON)
                        .config(config)
                        .when()
                        .get(endpoint)
                        .then()
                        .extract()
                        .response();

                // Log response to Extent
                ExtentLogger.logResponse(res);

                return res;
            } catch (Exception e) {
                attempt++;
                if (attempt > retries) {
                    throw e;
                }
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
                backoff *= 2;
            }
        }
    }

    public static Response post(String endpoint, Object body) {
        return post(endpoint, body, DEFAULT_RETRIES);
    }

    public static Response post(String endpoint, Object body, int retries) {
        int attempt = 0;
        long backoff = 500;
        while (true) {
            try {
                RestAssuredConfig config = RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, Config.getTimeoutMillis())
                                .setParam(CoreConnectionPNames.SO_TIMEOUT, Config.getTimeoutMillis())
                        );

                String base = Config.getBaseUrl();
                String fullUrl = base.endsWith("/") ? base.substring(0, base.length()-1) + endpoint : base + endpoint;

                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", ContentType.JSON.toString());

                // Log request to Extent
                ExtentLogger.logRequest("POST", fullUrl, body, headers);

                Response res = given()
                        .baseUri(Config.getBaseUrl())
                        .contentType(ContentType.JSON)
                        .config(config)
                        .body(body)
                        .when()
                        .post(endpoint)
                        .then()
                        .extract()
                        .response();

                // Log response to Extent
                ExtentLogger.logResponse(res);

                return res;
            } catch (Exception e) {
                attempt++;
                if (attempt > retries) {
                    throw e;
                }
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
                backoff *= 2;
            }
        }
    }
}