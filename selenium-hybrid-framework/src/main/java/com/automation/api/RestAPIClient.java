package com.automation.api;

import com.automation.config.ConfigManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.ConnectionConfig.connectionConfig;
import static io.restassured.config.HttpClientConfig.httpClientConfig;

/**
 * REST API Client for the Hybrid Automation Framework
 * Provides comprehensive API testing capabilities with RestAssured
 */
public class RestAPIClient {
    private static final Logger logger = LogManager.getLogger(RestAPIClient.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    private String baseUrl;
    private Map<String, String> defaultHeaders;
    private String authToken;
    private int connectionTimeout;
    private int readTimeout;
    
    public RestAPIClient() {
        this.baseUrl = ConfigManager.getInstance().getApiBaseUrl();
        this.connectionTimeout = ConfigManager.getInstance().getFrameworkConfig().getApi().getConnectionTimeout();
        this.readTimeout = ConfigManager.getInstance().getFrameworkConfig().getApi().getReadTimeout();
        this.defaultHeaders = new HashMap<>();
        initializeDefaultHeaders();
    }
    
    public RestAPIClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.connectionTimeout = ConfigManager.getInstance().getFrameworkConfig().getApi().getConnectionTimeout();
        this.readTimeout = ConfigManager.getInstance().getFrameworkConfig().getApi().getReadTimeout();
        this.defaultHeaders = new HashMap<>();
        initializeDefaultHeaders();
    }
    
    private void initializeDefaultHeaders() {
        defaultHeaders.put("Content-Type", "application/json");
        defaultHeaders.put("Accept", "application/json");
        defaultHeaders.put("User-Agent", "Hybrid-Automation-Framework/1.0");
    }
    
    /**
     * Set authentication token
     */
    public RestAPIClient setAuthToken(String token) {
        this.authToken = token;
        return this;
    }
    
    /**
     * Add default header
     */
    public RestAPIClient addDefaultHeader(String key, String value) {
        defaultHeaders.put(key, value);
        return this;
    }
    
    /**
     * Remove default header
     */
    public RestAPIClient removeDefaultHeader(String key) {
        defaultHeaders.remove(key);
        return this;
    }
    
    /**
     * Clear all default headers
     */
    public RestAPIClient clearDefaultHeaders() {
        defaultHeaders.clear();
        return this;
    }
    
    /**
     * GET request
     */
    public Response get(String endpoint) {
        return get(endpoint, null);
    }
    
    public Response get(String endpoint, Map<String, String> queryParams) {
        return get(endpoint, queryParams, null);
    }
    
    public Response get(String endpoint, Map<String, String> queryParams, Map<String, String> headers) {
        RequestSpecification request = createRequest(headers);
        
        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }
        
        logger.info("Sending GET request to: {}{}", baseUrl, endpoint);
        Response response = request.get(baseUrl + endpoint);
        logResponse(response);
        return response;
    }
    
    /**
     * POST request
     */
    public Response post(String endpoint, Object body) {
        return post(endpoint, body, null);
    }
    
    public Response post(String endpoint, Object body, Map<String, String> headers) {
        RequestSpecification request = createRequest(headers);
        
        if (body != null) {
            if (body instanceof String) {
                request.body((String) body);
            } else {
                request.body(body);
            }
        }
        
        logger.info("Sending POST request to: {}{}", baseUrl, endpoint);
        Response response = request.post(baseUrl + endpoint);
        logResponse(response);
        return response;
    }
    
    /**
     * PUT request
     */
    public Response put(String endpoint, Object body) {
        return put(endpoint, body, null);
    }
    
    public Response put(String endpoint, Object body, Map<String, String> headers) {
        RequestSpecification request = createRequest(headers);
        
        if (body != null) {
            if (body instanceof String) {
                request.body((String) body);
            } else {
                request.body(body);
            }
        }
        
        logger.info("Sending PUT request to: {}{}", baseUrl, endpoint);
        Response response = request.put(baseUrl + endpoint);
        logResponse(response);
        return response;
    }
    
    /**
     * DELETE request
     */
    public Response delete(String endpoint) {
        return delete(endpoint, null);
    }
    
    public Response delete(String endpoint, Map<String, String> headers) {
        RequestSpecification request = createRequest(headers);
        
        logger.info("Sending DELETE request to: {}{}", baseUrl, endpoint);
        Response response = request.delete(baseUrl + endpoint);
        logResponse(response);
        return response;
    }
    
    /**
     * PATCH request
     */
    public Response patch(String endpoint, Object body) {
        return patch(endpoint, body, null);
    }
    
    public Response patch(String endpoint, Object body, Map<String, String> headers) {
        RequestSpecification request = createRequest(headers);
        
        if (body != null) {
            if (body instanceof String) {
                request.body((String) body);
            } else {
                request.body(body);
            }
        }
        
        logger.info("Sending PATCH request to: {}{}", baseUrl, endpoint);
        Response response = request.patch(baseUrl + endpoint);
        logResponse(response);
        return response;
    }
    
    /**
     * Create request specification
     */
    private RequestSpecification createRequest(Map<String, String> additionalHeaders) {
        RequestSpecification request = given()
            .config(RestAssured.config()
                .connectionConfig(connectionConfig().connectTimeout(connectionTimeout))
                .httpClient(httpClientConfig().setReadTimeout(readTimeout)))
            .headers(createHeaders(additionalHeaders))
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);
        
        if (authToken != null && !authToken.isEmpty()) {
            request.header("Authorization", "Bearer " + authToken);
        }
        
        return request;
    }
    
    /**
     * Create headers
     */
    private Headers createHeaders(Map<String, String> additionalHeaders) {
        Headers headers = new Headers();
        
        // Add default headers
        for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
            headers.add(new Header(entry.getKey(), entry.getValue()));
        }
        
        // Add additional headers
        if (additionalHeaders != null) {
            for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
                headers.add(new Header(entry.getKey(), entry.getValue()));
            }
        }
        
        return headers;
    }
    
    /**
     * Log response details
     */
    private void logResponse(Response response) {
        logger.info("Response Status: {}", response.getStatusCode());
        logger.info("Response Time: {} ms", response.getTime());
        logger.debug("Response Headers: {}", response.getHeaders());
        logger.debug("Response Body: {}", response.getBody().asPrettyString());
    }
    
    /**
     * Validate response status code
     */
    public boolean validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        boolean isValid = actualStatusCode == expectedStatusCode;
        
        if (!isValid) {
            logger.error("Status code validation failed. Expected: {}, Actual: {}", expectedStatusCode, actualStatusCode);
        }
        
        return isValid;
    }
    
    /**
     * Validate response contains text
     */
    public boolean validateResponseContains(Response response, String expectedText) {
        String responseBody = response.getBody().asString();
        boolean contains = responseBody.contains(expectedText);
        
        if (!contains) {
            logger.error("Response validation failed. Expected text '{}' not found in response", expectedText);
        }
        
        return contains;
    }
    
    /**
     * Extract JSON value using JSONPath
     */
    public String extractJsonValue(Response response, String jsonPath) {
        try {
            return response.jsonPath().getString(jsonPath);
        } catch (Exception e) {
            logger.error("Failed to extract JSON value using path: {}", jsonPath, e);
            return null;
        }
    }
    
    /**
     * Extract JSON value as specific type
     */
    public <T> T extractJsonValue(Response response, String jsonPath, Class<T> type) {
        try {
            return response.jsonPath().getObject(jsonPath, type);
        } catch (Exception e) {
            logger.error("Failed to extract JSON value as type {} using path: {}", type.getSimpleName(), jsonPath, e);
            return null;
        }
    }
    
    /**
     * Convert response to JSON node
     */
    public JsonNode responseToJsonNode(Response response) {
        try {
            return objectMapper.readTree(response.getBody().asString());
        } catch (Exception e) {
            logger.error("Failed to convert response to JSON node", e);
            return null;
        }
    }
    
    /**
     * Convert response to specific object
     */
    public <T> T responseToObject(Response response, Class<T> type) {
        try {
            return objectMapper.readValue(response.getBody().asString(), type);
        } catch (Exception e) {
            logger.error("Failed to convert response to object of type: {}", type.getSimpleName(), e);
            return null;
        }
    }
    
    /**
     * Set base URL
     */
    public RestAPIClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
    
    /**
     * Get base URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * Reset to default configuration
     */
    public RestAPIClient reset() {
        this.authToken = null;
        this.defaultHeaders.clear();
        initializeDefaultHeaders();
        return this;
    }
}
