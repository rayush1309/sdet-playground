package com.automation.reporting;

import com.automation.config.ConfigManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Extent Report Manager for the Hybrid Automation Framework
 */
public class ExtentReportManager {
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    
    /**
     * Initialize Extent Report
     */
    public static void initializeReport() {
        if (extent == null) {
            extent = new ExtentReports();
            
            String reportPath = ConfigManager.getInstance().getProperty("report.path", "target/reports");
            File directory = new File(reportPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            String timestamp = LocalDateTime.now().format(formatter);
            String reportFile = String.format("%s/ExtentReport_%s.html", reportPath, timestamp);
            
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Hybrid Automation Framework - Test Report");
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss");
            
            extent.attachReporter(sparkReporter);
            
            // Set system info
            extent.setSystemInfo("Framework", "Hybrid Selenium + REST API Automation Framework");
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            
            logger.info("Extent Report initialized: {}", reportFile);
        }
    }
    
    /**
     * Start test in report
     */
    public static void startTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
        logger.info("Started test in Extent Report: {}", testName);
    }
    
    /**
     * End test in report
     */
    public static void endTest(ITestResult result) {
        ExtentTest extentTest = test.get();
        
        if (extentTest != null) {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    extentTest.log(Status.PASS, "Test passed successfully");
                    break;
                case ITestResult.FAILURE:
                    extentTest.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
                    break;
                case ITestResult.SKIP:
                    extentTest.log(Status.SKIP, "Test skipped: " + result.getThrowable().getMessage());
                    break;
                default:
                    extentTest.log(Status.INFO, "Test status: " + result.getStatus());
            }
            
            test.remove();
            logger.info("Ended test in Extent Report: {}", result.getName());
        }
    }
    
    /**
     * Log step in current test
     */
    public static void logStep(String step) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, "Step: " + step);
        }
    }
    
    /**
     * Log test data in current test
     */
    public static void logTestData(String key, String value) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, String.format("Test Data - %s: %s", key, value));
        }
    }
    
    /**
     * Log info message in current test
     */
    public static void logInfo(String message) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, message);
        }
    }
    
    /**
     * Log warning message in current test
     */
    public static void logWarning(String message) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.WARNING, message);
        }
    }
    
    /**
     * Log error message in current test
     */
    public static void logError(String message) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.ERROR, message);
        }
    }
    
    /**
     * Log exception in current test
     */
    public static void logException(Throwable throwable) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.FATAL, "Exception occurred: " + throwable.getMessage());
            extentTest.log(Status.FATAL, throwable);
        }
    }
    
    /**
     * Add screenshot to current test
     */
    public static void addScreenshot(String screenshotPath) {
        ExtentTest extentTest = test.get();
        if (extentTest != null && screenshotPath != null) {
            try {
                File screenshotFile = new File(screenshotPath);
                if (screenshotFile.exists()) {
                    extentTest.addScreenCaptureFromPath(screenshotPath);
                    logger.info("Screenshot added to report: {}", screenshotPath);
                } else {
                    logger.warn("Screenshot file not found: {}", screenshotPath);
                }
            } catch (Exception e) {
                logger.error("Failed to add screenshot to report: {}", screenshotPath, e);
            }
        }
    }
    
    /**
     * Add base64 screenshot to current test
     */
    public static void addBase64Screenshot(String base64String, String title) {
        ExtentTest extentTest = test.get();
        if (extentTest != null && base64String != null) {
            try {
                extentTest.addScreenCaptureFromBase64String(base64String, title);
                logger.info("Base64 screenshot added to report: {}", title);
            } catch (Exception e) {
                logger.error("Failed to add base64 screenshot to report: {}", title, e);
            }
        }
    }
    
    /**
     * Log API request details
     */
    public static void logAPIRequest(String method, String url, String headers, String body) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, "API Request Details:");
            extentTest.log(Status.INFO, "Method: " + method);
            extentTest.log(Status.INFO, "URL: " + url);
            if (headers != null) {
                extentTest.log(Status.INFO, "Headers: " + headers);
            }
            if (body != null) {
                extentTest.log(Status.INFO, "Body: " + body);
            }
        }
    }
    
    /**
     * Log API response details
     */
    public static void logAPIResponse(int statusCode, String headers, String body, long responseTime) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, "API Response Details:");
            extentTest.log(Status.INFO, "Status Code: " + statusCode);
            extentTest.log(Status.INFO, "Response Time: " + responseTime + " ms");
            if (headers != null) {
                extentTest.log(Status.INFO, "Response Headers: " + headers);
            }
            if (body != null) {
                extentTest.log(Status.INFO, "Response Body: " + body);
            }
        }
    }
    
    /**
     * Log database query
     */
    public static void logDatabaseQuery(String query, Object[] parameters) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, "Database Query: " + query);
            if (parameters != null && parameters.length > 0) {
                StringBuilder params = new StringBuilder("Parameters: ");
                for (int i = 0; i < parameters.length; i++) {
                    params.append("[").append(i).append("]=").append(parameters[i]);
                    if (i < parameters.length - 1) {
                        params.append(", ");
                    }
                }
                extentTest.log(Status.INFO, params.toString());
            }
        }
    }
    
    /**
     * Log database result
     */
    public static void logDatabaseResult(String result) {
        ExtentTest extentTest = test.get();
        if (extentTest != null) {
            extentTest.log(Status.INFO, "Database Result: " + result);
        }
    }
    
    /**
     * Flush report
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Report flushed successfully");
        }
    }
    
    /**
     * Get current test
     */
    public static ExtentTest getCurrentTest() {
        return test.get();
    }
    
    /**
     * Get extent reports instance
     */
    public static ExtentReports getExtent() {
        return extent;
    }
}
