package com.automation.core;

import com.automation.api.RestAPIClient;
import com.automation.config.ConfigManager;
import com.automation.reporting.ExtentReportManager;
import com.automation.utils.ScreenshotUtils;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

/**
 * Base Test Class for the Hybrid Automation Framework
 * Provides both UI and API testing capabilities
 */
public abstract class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    protected WebDriver driver;
    protected RestAPIClient apiClient;
    protected String testName;
    protected String testDescription;
    
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        logger.info("Starting test suite: {}", context.getSuite().getName());
        ExtentReportManager.initializeReport();
    }
    
    @BeforeTest(alwaysRun = true)
    public void beforeTest(ITestContext context) {
        logger.info("Starting test: {}", context.getCurrentXmlTest().getName());
    }
    
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        logger.info("Starting test class: {}", this.getClass().getSimpleName());
    }
    
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, ITestResult result) {
        testName = method.getName();
        testDescription = method.getAnnotation(Test.class) != null ? 
            method.getAnnotation(Test.class).description() : "";
        
        logger.info("Starting test method: {} - {}", testName, testDescription);
        
        // Initialize API client
        apiClient = new RestAPIClient();
        
        // Initialize WebDriver if UI test
        if (isUITest()) {
            initializeWebDriver();
        }
        
        // Start test in Extent Report
        ExtentReportManager.startTest(testName, testDescription);
    }
    
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        logger.info("Finishing test method: {} - Status: {}", testName, result.getStatus());
        
        // Take screenshot on failure for UI tests
        if (result.getStatus() == ITestResult.FAILURE && isUITest() && driver != null) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, testName);
            attachScreenshotToReport(screenshotPath);
            attachScreenshotToAllure(screenshotPath);
        }
        
        // Quit WebDriver
        if (driver != null) {
            DriverManager.quitDriver();
            driver = null;
        }
        
        // End test in Extent Report
        ExtentReportManager.endTest(result);
    }
    
    @AfterClass(alwaysRun = true)
    public void afterClass() {
        logger.info("Finishing test class: {}", this.getClass().getSimpleName());
    }
    
    @AfterTest(alwaysRun = true)
    public void afterTest(ITestContext context) {
        logger.info("Finishing test: {}", context.getCurrentXmlTest().getName());
    }
    
    @AfterSuite(alwaysRun = true)
    public void afterSuite(ITestContext context) {
        logger.info("Finishing test suite: {}", context.getSuite().getName());
        ExtentReportManager.flushReport();
        DriverManager.quitAllDrivers();
    }
    
    /**
     * Initialize WebDriver for UI tests
     */
    protected void initializeWebDriver() {
        driver = DriverManager.getDriver();
        logger.info("WebDriver initialized for test: {}", testName);
    }
    
    /**
     * Check if this is a UI test
     */
    protected boolean isUITest() {
        // Override in subclasses to specify test type
        return true;
    }
    
    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        if (driver != null) {
            driver.get(url);
            logger.info("Navigated to: {}", url);
        } else {
            logger.error("WebDriver not initialized");
        }
    }
    
    /**
     * Get current page title
     */
    protected String getPageTitle() {
        return driver != null ? driver.getTitle() : null;
    }
    
    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver != null ? driver.getCurrentUrl() : null;
    }
    
    /**
     * Wait for page to load
     */
    protected void waitForPageLoad() {
        if (driver != null) {
            DriverManager.getWait().until(webDriver -> 
                ((org.openqa.selenium.JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        }
    }
    
    /**
     * Refresh page
     */
    protected void refreshPage() {
        if (driver != null) {
            driver.navigate().refresh();
            logger.info("Page refreshed");
        }
    }
    
    /**
     * Go back to previous page
     */
    protected void goBack() {
        if (driver != null) {
            driver.navigate().back();
            logger.info("Navigated back to previous page");
        }
    }
    
    /**
     * Go forward to next page
     */
    protected void goForward() {
        if (driver != null) {
            driver.navigate().forward();
            logger.info("Navigated forward to next page");
        }
    }
    
    /**
     * Get API client
     */
    protected RestAPIClient getApiClient() {
        return apiClient;
    }
    
    /**
     * Set API base URL
     */
    protected void setApiBaseUrl(String baseUrl) {
        if (apiClient != null) {
            apiClient.setBaseUrl(baseUrl);
        }
    }
    
    /**
     * Set API authentication token
     */
    protected void setApiAuthToken(String token) {
        if (apiClient != null) {
            apiClient.setAuthToken(token);
        }
    }
    
    /**
     * Add API header
     */
    protected void addApiHeader(String key, String value) {
        if (apiClient != null) {
            apiClient.addDefaultHeader(key, value);
        }
    }
    
    /**
     * Attach screenshot to Extent Report
     */
    private void attachScreenshotToReport(String screenshotPath) {
        try {
            ExtentReportManager.addScreenshot(screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Extent Report", e);
        }
    }
    
    /**
     * Attach screenshot to Allure Report
     */
    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshotToAllure(String screenshotPath) {
        try {
            return ScreenshotUtils.getScreenshotAsBytes(screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Allure Report", e);
            return new byte[0];
        }
    }
    
    /**
     * Log test step
     */
    protected void logStep(String step) {
        logger.info("Test Step: {}", step);
        ExtentReportManager.logStep(step);
    }
    
    /**
     * Log test data
     */
    protected void logTestData(String key, String value) {
        logger.info("Test Data - {}: {}", key, value);
        ExtentReportManager.logTestData(key, value);
    }
    
    /**
     * Assert with custom message
     */
    protected void assertTrue(boolean condition, String message) {
        if (!condition) {
            logger.error("Assertion failed: {}", message);
            throw new AssertionError(message);
        }
        logger.info("Assertion passed: {}", message);
    }
    
    /**
     * Assert equals with custom message
     */
    protected void assertEquals(Object actual, Object expected, String message) {
        if (!java.util.Objects.equals(actual, expected)) {
            logger.error("Assertion failed: {} - Expected: {}, Actual: {}", message, expected, actual);
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
        logger.info("Assertion passed: {} - Value: {}", message, actual);
    }
    
    /**
     * Sleep for specified milliseconds
     */
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Sleep interrupted");
        }
    }
}
