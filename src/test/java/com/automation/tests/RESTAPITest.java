package com.automation.tests;

import com.automation.core.BaseTest;
import com.automation.reporting.ExtentReportManager;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Sample REST API Test demonstrating API testing capabilities
 */
public class RESTAPITest extends BaseTest {
    
    @Override
    protected boolean isUITest() {
        return false; // This is an API-only test
    }
    
    @Test(description = "Test GET request to fetch all posts")
    public void testGetAllPosts() {
        logStep("Set API base URL to JSONPlaceholder");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Send GET request to /posts endpoint");
        Response response = getApiClient().get("/posts");
        
        logStep("Validate response status code is 200");
        assertTrue(getApiClient().validateStatusCode(response, 200), 
            "Response status code should be 200");
        
        logStep("Validate response contains posts data");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("userId"), "Response should contain 'userId' field");
        assertTrue(responseBody.contains("id"), "Response should contain 'id' field");
        assertTrue(responseBody.contains("title"), "Response should contain 'title' field");
        assertTrue(responseBody.contains("body"), "Response should contain 'body' field");
        
        logStep("Validate response structure");
        int postCount = response.jsonPath().getList("$").size();
        assertTrue(postCount > 0, "Should return at least one post");
        assertEquals(postCount, 100, "Should return exactly 100 posts");
        
        logTestData("Endpoint", "/posts");
        logTestData("Method", "GET");
        logTestData("Expected Status", "200");
        logTestData("Actual Status", String.valueOf(response.getStatusCode()));
        logTestData("Posts Count", String.valueOf(postCount));
        
        // Log API details to Extent Report
        ExtentReportManager.logAPIRequest("GET", "https://jsonplaceholder.typicode.com/posts", null, null);
        ExtentReportManager.logAPIResponse(response.getStatusCode(), response.getHeaders().toString(), 
            response.getBody().asPrettyString(), response.getTime());
    }
    
    @Test(description = "Test GET request to fetch specific post by ID")
    public void testGetPostById() {
        logStep("Set API base URL to JSONPlaceholder");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Send GET request to /posts/1 endpoint");
        Response response = getApiClient().get("/posts/1");
        
        logStep("Validate response status code is 200");
        assertTrue(getApiClient().validateStatusCode(response, 200), 
            "Response status code should be 200");
        
        logStep("Validate specific post data");
        int userId = response.jsonPath().getInt("userId");
        int id = response.jsonPath().getInt("id");
        String title = response.jsonPath().getString("title");
        String body = response.jsonPath().getString("body");
        
        assertEquals(userId, 1, "User ID should be 1");
        assertEquals(id, 1, "Post ID should be 1");
        assertNotNull(title, "Title should not be null");
        assertNotNull(body, "Body should not be null");
        assertTrue(title.length() > 0, "Title should not be empty");
        assertTrue(body.length() > 0, "Body should not be empty");
        
        logTestData("Endpoint", "/posts/1");
        logTestData("Method", "GET");
        logTestData("Expected Status", "200");
        logTestData("Actual Status", String.valueOf(response.getStatusCode()));
        logTestData("User ID", String.valueOf(userId));
        logTestData("Post ID", String.valueOf(id));
        logTestData("Title", title);
        
        // Log API details to Extent Report
        ExtentReportManager.logAPIRequest("GET", "https://jsonplaceholder.typicode.com/posts/1", null, null);
        ExtentReportManager.logAPIResponse(response.getStatusCode(), response.getHeaders().toString(), 
            response.getBody().asPrettyString(), response.getTime());
    }
    
    @Test(description = "Test POST request to create new post")
    public void testCreatePost() {
        logStep("Set API base URL to JSONPlaceholder");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Prepare post data");
        Map<String, Object> postData = new HashMap<>();
        postData.put("title", "Test Post Title");
        postData.put("body", "This is a test post body for automation testing");
        postData.put("userId", 1);
        
        logStep("Send POST request to /posts endpoint");
        Response response = getApiClient().post("/posts", postData);
        
        logStep("Validate response status code is 201 (Created)");
        assertTrue(getApiClient().validateStatusCode(response, 201), 
            "Response status code should be 201");
        
        logStep("Validate response contains created post data");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("id"), "Response should contain 'id' field");
        assertTrue(responseBody.contains("title"), "Response should contain 'title' field");
        assertTrue(responseBody.contains("body"), "Response should contain 'body' field");
        assertTrue(responseBody.contains("userId"), "Response should contain 'userId' field");
        
        logStep("Validate response data matches request data");
        String title = response.jsonPath().getString("title");
        String body = response.jsonPath().getString("body");
        int userId = response.jsonPath().getInt("userId");
        
        assertEquals(title, "Test Post Title", "Title should match request data");
        assertEquals(body, "This is a test post body for automation testing", "Body should match request data");
        assertEquals(userId, 1, "User ID should match request data");
        
        logTestData("Endpoint", "/posts");
        logTestData("Method", "POST");
        logTestData("Expected Status", "201");
        logTestData("Actual Status", String.valueOf(response.getStatusCode()));
        logTestData("Request Title", "Test Post Title");
        logTestData("Response Title", title);
        
        // Log API details to Extent Report
        ExtentReportManager.logAPIRequest("POST", "https://jsonplaceholder.typicode.com/posts", 
            "Content-Type: application/json", postData.toString());
        ExtentReportManager.logAPIResponse(response.getStatusCode(), response.getHeaders().toString(), 
            response.getBody().asPrettyString(), response.getTime());
    }
    
    @Test(description = "Test PUT request to update existing post")
    public void testUpdatePost() {
        logStep("Set API base URL to JSONPlaceholder");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Prepare updated post data");
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("id", 1);
        updatedData.put("title", "Updated Post Title");
        updatedData.put("body", "This is an updated post body for automation testing");
        updatedData.put("userId", 1);
        
        logStep("Send PUT request to /posts/1 endpoint");
        Response response = getApiClient().put("/posts/1", updatedData);
        
        logStep("Validate response status code is 200");
        assertTrue(getApiClient().validateStatusCode(response, 200), 
            "Response status code should be 200");
        
        logStep("Validate response contains updated post data");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("id"), "Response should contain 'id' field");
        assertTrue(responseBody.contains("title"), "Response should contain 'title' field");
        assertTrue(responseBody.contains("body"), "Response should contain 'body' field");
        
        logStep("Validate response data matches updated data");
        int id = response.jsonPath().getInt("id");
        String title = response.jsonPath().getString("title");
        String body = response.jsonPath().getString("body");
        
        assertEquals(id, 1, "Post ID should be 1");
        assertEquals(title, "Updated Post Title", "Title should match updated data");
        assertEquals(body, "This is an updated post body for automation testing", "Body should match updated data");
        
        logTestData("Endpoint", "/posts/1");
        logTestData("Method", "PUT");
        logTestData("Expected Status", "200");
        logTestData("Actual Status", String.valueOf(response.getStatusCode()));
        logTestData("Updated Title", "Updated Post Title");
        logTestData("Response Title", title);
        
        // Log API details to Extent Report
        ExtentReportManager.logAPIRequest("PUT", "https://jsonplaceholder.typicode.com/posts/1", 
            "Content-Type: application/json", updatedData.toString());
        ExtentReportManager.logAPIResponse(response.getStatusCode(), response.getHeaders().toString(), 
            response.getBody().asPrettyString(), response.getTime());
    }
    
    @Test(description = "Test DELETE request to remove post")
    public void testDeletePost() {
        logStep("Set API base URL to JSONPlaceholder");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Send DELETE request to /posts/1 endpoint");
        Response response = getApiClient().delete("/posts/1");
        
        logStep("Validate response status code is 200");
        assertTrue(getApiClient().validateStatusCode(response, 200), 
            "Response status code should be 200");
        
        logStep("Validate response is empty (typical for DELETE operations)");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.isEmpty() || responseBody.equals("{}"), 
            "Response body should be empty for DELETE operation");
        
        logTestData("Endpoint", "/posts/1");
        logTestData("Method", "DELETE");
        logTestData("Expected Status", "200");
        logTestData("Actual Status", String.valueOf(response.getStatusCode()));
        logTestData("Response Body", responseBody.isEmpty() ? "Empty" : responseBody);
        
        // Log API details to Extent Report
        ExtentReportManager.logAPIRequest("DELETE", "https://jsonplaceholder.typicode.com/posts/1", null, null);
        ExtentReportManager.logAPIResponse(response.getStatusCode(), response.getHeaders().toString(), 
            response.getBody().asString(), response.getTime());
    }
    
    @Test(description = "Test API with query parameters")
    public void testGetPostsWithQueryParams() {
        logStep("Set API base URL to JSONPlaceholder");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Prepare query parameters");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("userId", "1");
        
        logStep("Send GET request to /posts with userId query parameter");
        Response response = getApiClient().get("/posts", queryParams);
        
        logStep("Validate response status code is 200");
        assertTrue(getApiClient().validateStatusCode(response, 200), 
            "Response status code should be 200");
        
        logStep("Validate all returned posts belong to user 1");
        int postCount = response.jsonPath().getList("$").size();
        assertTrue(postCount > 0, "Should return at least one post");
        
        // Verify all posts have userId = 1
        for (int i = 0; i < postCount; i++) {
            int userId = response.jsonPath().getInt("[" + i + "].userId");
            assertEquals(userId, 1, "All posts should belong to user 1");
        }
        
        logTestData("Endpoint", "/posts");
        logTestData("Method", "GET");
        logTestData("Query Parameters", "userId=1");
        logTestData("Expected Status", "200");
        logTestData("Actual Status", String.valueOf(response.getStatusCode()));
        logTestData("Posts Count", String.valueOf(postCount));
        
        // Log API details to Extent Report
        ExtentReportManager.logAPIRequest("GET", "https://jsonplaceholder.typicode.com/posts?userId=1", null, null);
        ExtentReportManager.logAPIResponse(response.getStatusCode(), response.getHeaders().toString(), 
            response.getBody().asPrettyString(), response.getTime());
    }
}
