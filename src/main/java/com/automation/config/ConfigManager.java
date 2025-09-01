package com.automation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager for the Hybrid Automation Framework
 * Handles both UI and API testing configurations
 */
public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private Properties properties;
    private FrameworkConfig frameworkConfig;
    
    private ConfigManager() {
        loadProperties();
        loadFrameworkConfig();
    }
    
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    private void loadProperties() {
        properties = new Properties();
        try {
            String env = System.getProperty("env", "qa");
            properties.load(getClass().getClassLoader().getResourceAsStream("config/application-" + env + ".properties"));
            logger.info("Loaded configuration for environment: {}", env);
        } catch (IOException e) {
            logger.error("Failed to load properties file", e);
            loadDefaultProperties();
        }
    }
    
    private void loadFrameworkConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            frameworkConfig = mapper.readValue(
                getClass().getClassLoader().getResourceAsStream("config/framework-config.yml"),
                FrameworkConfig.class
            );
            logger.info("Loaded framework configuration successfully");
        } catch (IOException e) {
            logger.error("Failed to load framework configuration", e);
            frameworkConfig = new FrameworkConfig();
        }
    }
    
    private void loadDefaultProperties() {
        properties.setProperty("browser", "chrome");
        properties.setProperty("headless", "false");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "20");
        properties.setProperty("base.url", "https://www.google.com");
        properties.setProperty("api.base.url", "https://api.example.com");
        properties.setProperty("screenshot.path", "target/screenshots");
        properties.setProperty("report.path", "target/reports");
        logger.info("Loaded default properties");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
    
    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
    
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public FrameworkConfig getFrameworkConfig() {
        return frameworkConfig;
    }
    
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    public String getBaseUrl() {
        return getProperty("base.url");
    }
    
    public String getApiBaseUrl() {
        return getProperty("api.base.url");
    }
    
    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }
    
    public int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }
    
    public int getExplicitWait() {
        return getIntProperty("explicit.wait", 20);
    }
}
