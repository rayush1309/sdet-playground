package com.automation.tests;

import com.automation.core.BaseTest;
import com.automation.core.DriverManager;
import com.automation.reporting.ExtentReportManager;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Hybrid Test demonstrating UI + API testing combination
 * This test shows how to use both UI automation and API testing in a single test scenario
 */
public class HybridUITest extends BaseTest {
    
    @Test(description = "Hybrid test: Search on Google UI and validate results via API")
    public void testHybridGoogleSearch() {
        logStep("=== PHASE 1: UI Testing - Google Search ===");
        
        logStep("Navigate to Google homepage");
        navigateTo("https://www.google.com");
        waitForPageLoad();
        
        logStep("Verify page title contains 'Google'");
        String pageTitle = getPageTitle();
        assertTrue(pageTitle.contains("Google"), "Page title should contain 'Google'");
        
        logStep("Find and interact with search box");
        WebElement searchBox = DriverManager.getWait().until(
            ExpectedConditions.elementToBeClickable(By.name("q"))
        );
        
        logStep("Enter search term 'Selenium WebDriver'");
        searchBox.clear();
        searchBox.sendKeys("Selenium WebDriver");
        
        logStep("Submit search");
        searchBox.sendKeys(Keys.RETURN);
        
        logStep("Wait for search results to load");
        WebDriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.id("search"))
        );
        
        logStep("Verify search results are displayed");
        WebElement searchResults = driver.findElement(By.id("search"));
        assertTrue(searchResults.isDisplayed(), "Search results should be displayed");
        
        logStep("Capture search results count from UI");
        int uiResultCount = searchResults.findElements(By.cssSelector("div.g")).size();
        assertTrue(uiResultCount > 0, "Should have at least one search result");
        
        logTestData("UI Search Term", "Selenium WebDriver");
        logTestData("UI Results Count", String.valueOf(uiResultCount));
        
        logStep("=== PHASE 2: API Testing - Validate Search Data ===");
        
        logStep("Set API base URL for a hypothetical search API");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Simulate API call to get search-related data");
        Response response = getApiClient().get("/posts");
        
        logStep("Validate API response status code");
        assertTrue(getApiClient().validateStatusCode(response, 200), 
            "API response status code should be 200");
        
        logStep("Validate API response contains relevant data");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("title"), "API response should contain 'title' field");
        assertTrue(responseBody.contains("body"), "API response should contain 'body' field");
        
        logStep("Extract and validate API data");
        int apiDataCount = response.jsonPath().getList("$").size();
        assertTrue(apiDataCount > 0, "API should return data");
        
        logTestData("API Endpoint", "/posts");
        logTestData("API Data Count", String.valueOf(apiDataCount));
        
        logStep("=== PHASE 3: Data Correlation ===");
        
        logStep("Correlate UI and API data");
        assertTrue(uiResultCount > 0 && apiDataCount > 0, 
            "Both UI and API should return data");
        
        logStep("Log correlation results");
        logTestData("UI Results Available", "Yes");
        logTestData("API Data Available", "Yes");
        logTestData("Data Correlation", "Successful");
        
        // Log comprehensive test details to Extent Report
        ExtentReportManager.logInfo("=== HYBRID TEST COMPLETED ===");
        ExtentReportManager.logInfo("UI Search Results: " + uiResultCount);
        ExtentReportManager.logInfo("API Data Count: " + apiDataCount);
        ExtentReportManager.logInfo("Test combines both UI automation and API testing");
    }
    
    @Test(description = "Hybrid test: Form submission with API validation")
    public void testHybridFormSubmission() {
        logStep("=== PHASE 1: UI Testing - Form Interaction ===");
        
        logStep("Navigate to a test form page");
        navigateTo("https://httpbin.org/forms/post");
        waitForPageLoad();
        
        logStep("Verify form page loaded");
        String pageTitle = getPageTitle();
        assertTrue(pageTitle.contains("httpbin"), "Page should be httpbin form page");
        
        logStep("Find form elements");
        WebElement customerNameField = driver.findElement(By.name("custname"));
        WebElement telephoneField = driver.findElement(By.name("custtel"));
        WebElement emailField = driver.findElement(By.name("custemail"));
        WebElement sizeSelect = driver.findElement(By.name("size"));
        WebElement toppingsCheckboxes = driver.findElement(By.name("topping"));
        WebElement deliveryTimeField = driver.findElement(By.name("delivery"));
        WebElement commentsField = driver.findElement(By.name("comments"));
        
        logStep("Fill form with test data");
        customerNameField.sendKeys("John Doe");
        telephoneField.sendKeys("123-456-7890");
        emailField.sendKeys("john.doe@example.com");
        sizeSelect.sendKeys("large");
        toppingsCheckboxes.click();
        deliveryTimeField.sendKeys("20:00");
        commentsField.sendKeys("Test order for automation");
        
        logStep("Capture form data for API validation");
        String customerName = customerNameField.getAttribute("value");
        String telephone = telephoneField.getAttribute("value");
        String email = emailField.getAttribute("value");
        String size = sizeSelect.getAttribute("value");
        String comments = commentsField.getAttribute("value");
        
        logTestData("Customer Name", customerName);
        logTestData("Telephone", telephone);
        logTestData("Email", email);
        logTestData("Size", size);
        logTestData("Comments", comments);
        
        logStep("=== PHASE 2: API Testing - Validate Form Data ===");
        
        logStep("Set API base URL for form validation");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Create test data object matching form data");
        Map<String, Object> formData = new HashMap<>();
        formData.put("customerName", customerName);
        formData.put("telephone", telephone);
        formData.put("email", email);
        formData.put("size", size);
        formData.put("comments", comments);
        
        logStep("Send POST request to simulate form submission");
        Response response = getApiClient().post("/posts", formData);
        
        logStep("Validate API response");
        assertTrue(getApiClient().validateStatusCode(response, 201), 
            "API response status code should be 201");
        
        logStep("Validate API response contains submitted data");
        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("customerName"), "Response should contain customer name");
        assertTrue(responseBody.contains("email"), "Response should contain email");
        
        logStep("=== PHASE 3: Data Validation ===");
        
        logStep("Verify form data integrity");
        assertEquals(customerName, "John Doe", "Customer name should match");
        assertEquals(telephone, "123-456-7890", "Telephone should match");
        assertEquals(email, "john.doe@example.com", "Email should match");
        assertEquals(size, "large", "Size should match");
        assertEquals(comments, "Test order for automation", "Comments should match");
        
        logStep("Log validation results");
        logTestData("Form Data Validation", "All fields validated successfully");
        logTestData("API Response Status", String.valueOf(response.getStatusCode()));
        logTestData("Data Integrity", "Maintained");
        
        // Log comprehensive test details to Extent Report
        ExtentReportManager.logInfo("=== HYBRID FORM TEST COMPLETED ===");
        ExtentReportManager.logInfo("Form Fields Filled: 7");
        ExtentReportManager.logInfo("API Response Status: " + response.getStatusCode());
        ExtentReportManager.logInfo("Data Validation: Successful");
    }
    
    @Test(description = "Hybrid test: Performance validation with UI and API")
    public void testHybridPerformanceValidation() {
        logStep("=== PHASE 1: UI Performance Testing ===");
        
        logStep("Start timing UI operations");
        long startTime = System.currentTimeMillis();
        
        logStep("Navigate to Google homepage");
        navigateTo("https://www.google.com");
        waitForPageLoad();
        
        logStep("Measure page load time");
        long pageLoadTime = System.currentTimeMillis() - startTime;
        
        logStep("Verify page loaded within acceptable time");
        assertTrue(pageLoadTime < 10000, "Page should load within 10 seconds");
        
        logTestData("UI Page Load Time (ms)", String.valueOf(pageLoadTime));
        logTestData("UI Performance Threshold (ms)", "10000");
        logTestData("UI Performance Status", pageLoadTime < 10000 ? "PASS" : "FAIL");
        
        logStep("=== PHASE 2: API Performance Testing ===");
        
        logStep("Set API base URL");
        setApiBaseUrl("https://jsonplaceholder.typicode.com");
        
        logStep("Start timing API operations");
        long apiStartTime = System.currentTimeMillis();
        
        logStep("Send API request");
        Response response = getApiClient().get("/posts");
        
        logStep("Measure API response time");
        long apiResponseTime = System.currentTimeMillis() - apiStartTime;
        
        logStep("Validate API response time");
        assertTrue(apiResponseTime < 5000, "API should respond within 5 seconds");
        
        logTestData("API Response Time (ms)", String.valueOf(apiResponseTime));
        logTestData("API Performance Threshold (ms)", "5000");
        logTestData("API Performance Status", apiResponseTime < 5000 ? "PASS" : "FAIL");
        
        logStep("=== PHASE 3: Performance Analysis ===");
        
        logStep("Compare UI vs API performance");
        double performanceRatio = (double) apiResponseTime / pageLoadTime;
        
        logStep("Log performance analysis");
        logTestData("Performance Ratio (API/UI)", String.format("%.2f", performanceRatio));
        logTestData("Faster Operation", pageLoadTime < apiResponseTime ? "UI" : "API");
        
        logStep("Overall performance validation");
        boolean overallPerformance = pageLoadTime < 10000 && apiResponseTime < 5000;
        assertTrue(overallPerformance, "Both UI and API should meet performance requirements");
        
        logTestData("Overall Performance", overallPerformance ? "PASS" : "FAIL");
        
        // Log comprehensive test details to Extent Report
        ExtentReportManager.logInfo("=== HYBRID PERFORMANCE TEST COMPLETED ===");
        ExtentReportManager.logInfo("UI Load Time: " + pageLoadTime + "ms");
        ExtentReportManager.logInfo("API Response Time: " + apiResponseTime + "ms");
        ExtentReportManager.logInfo("Performance Ratio: " + String.format("%.2f", performanceRatio));
        ExtentReportManager.logInfo("Overall Status: " + (overallPerformance ? "PASS" : "FAIL"));
    }
}
