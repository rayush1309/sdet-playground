package com.automation.core;

import com.automation.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Advanced WebDriver Manager for the Hybrid Automation Framework
 * Supports multiple browsers, parallel execution, and advanced configurations
 */
public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final Map<Long, WebDriver> driverThreadMap = new ConcurrentHashMap<>();
    private static final Map<Long, WebDriverWait> waitThreadMap = new ConcurrentHashMap<>();
    
    private DriverManager() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Initialize WebDriver for the current thread
     */
    public static WebDriver getDriver() {
        long threadId = Thread.currentThread().getId();
        if (!driverThreadMap.containsKey(threadId)) {
            WebDriver driver = createDriver();
            driverThreadMap.put(threadId, driver);
            logger.info("Created new WebDriver instance for thread: {}", threadId);
        }
        return driverThreadMap.get(threadId);
    }
    
    /**
     * Get WebDriverWait for the current thread
     */
    public static WebDriverWait getWait() {
        long threadId = Thread.currentThread().getId();
        if (!waitThreadMap.containsKey(threadId)) {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(ConfigManager.getInstance().getExplicitWait()));
            waitThreadMap.put(threadId, wait);
        }
        return waitThreadMap.get(threadId);
    }
    
    /**
     * Create WebDriver instance based on configuration
     */
    private static WebDriver createDriver() {
        String browser = ConfigManager.getInstance().getBrowser().toLowerCase();
        boolean headless = ConfigManager.getInstance().isHeadless();
        
        switch (browser) {
            case "chrome":
                return createChromeDriver(headless);
            case "firefox":
                return createFirefoxDriver(headless);
            case "edge":
                return createEdgeDriver(headless);
            case "safari":
                return createSafariDriver();
            default:
                logger.warn("Unsupported browser: {}. Defaulting to Chrome", browser);
                return createChromeDriver(headless);
        }
    }
    
    /**
     * Create Chrome WebDriver with advanced options
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Performance optimizations
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // Custom preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);
        
        // Exclude automation flags
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getInstance().getImplicitWait()));
        driver.manage().window().maximize();
        
        logger.info("Chrome WebDriver created successfully");
        return driver;
    }
    
    /**
     * Create Firefox WebDriver with advanced options
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Performance optimizations
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getInstance().getImplicitWait()));
        driver.manage().window().maximize();
        
        logger.info("Firefox WebDriver created successfully");
        return driver;
    }
    
    /**
     * Create Edge WebDriver with advanced options
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Performance optimizations
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");
        
        WebDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getInstance().getImplicitWait()));
        driver.manage().window().maximize();
        
        logger.info("Edge WebDriver created successfully");
        return driver;
    }
    
    /**
     * Create Safari WebDriver
     */
    private static WebDriver createSafariDriver() {
        WebDriverManager.safaridriver().setup();
        SafariOptions options = new SafariOptions();
        
        WebDriver driver = new SafariDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getInstance().getImplicitWait()));
        driver.manage().window().maximize();
        
        logger.info("Safari WebDriver created successfully");
        return driver;
    }
    
    /**
     * Quit WebDriver for the current thread
     */
    public static void quitDriver() {
        long threadId = Thread.currentThread().getId();
        WebDriver driver = driverThreadMap.get(threadId);
        
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully for thread: {}", threadId);
            } catch (Exception e) {
                logger.error("Error quitting WebDriver for thread: {}", threadId, e);
            } finally {
                driverThreadMap.remove(threadId);
                waitThreadMap.remove(threadId);
            }
        }
    }
    
    /**
     * Quit all WebDriver instances
     */
    public static void quitAllDrivers() {
        driverThreadMap.values().forEach(driver -> {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.error("Error quitting WebDriver", e);
            }
        });
        driverThreadMap.clear();
        waitThreadMap.clear();
        logger.info("All WebDriver instances quit successfully");
    }
    
    /**
     * Get current WebDriver instance
     */
    public static WebDriver getCurrentDriver() {
        return getDriver();
    }
    
    /**
     * Navigate to URL
     */
    public static void navigateTo(String url) {
        getDriver().get(url);
        logger.info("Navigated to: {}", url);
    }
    
    /**
     * Get current URL
     */
    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
    
    /**
     * Get page title
     */
    public static String getPageTitle() {
        return getDriver().getTitle();
    }
}
