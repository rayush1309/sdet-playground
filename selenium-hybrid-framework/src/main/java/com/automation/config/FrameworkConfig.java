package com.automation.config;

import java.util.List;
import java.util.Map;

/**
 * Framework Configuration class for UI and API testing settings
 */
public class FrameworkConfig {
    private UIConfig ui;
    private APIConfig api;
    private ReportingConfig reporting;
    private DatabaseConfig database;
    private PerformanceConfig performance;
    
    public FrameworkConfig() {
        // Default configurations
        this.ui = new UIConfig();
        this.api = new APIConfig();
        this.reporting = new ReportingConfig();
        this.database = new DatabaseConfig();
        this.performance = new PerformanceConfig();
    }
    
    // Getters and Setters
    public UIConfig getUi() { return ui; }
    public void setUi(UIConfig ui) { this.ui = ui; }
    
    public APIConfig getApi() { return api; }
    public void setApi(APIConfig api) { this.api = api; }
    
    public ReportingConfig getReporting() { return reporting; }
    public void setReporting(ReportingConfig reporting) { this.reporting = reporting; }
    
    public DatabaseConfig getDatabase() { return database; }
    public void setDatabase(DatabaseConfig database) { this.database = database; }
    
    public PerformanceConfig getPerformance() { return performance; }
    public void setPerformance(PerformanceConfig performance) { this.performance = performance; }
    
    public static class UIConfig {
        private List<String> supportedBrowsers;
        private Map<String, String> browserOptions;
        private int defaultTimeout;
        private boolean takeScreenshots;
        private String screenshotFormat;
        
        public UIConfig() {
            this.defaultTimeout = 30;
            this.takeScreenshots = true;
            this.screenshotFormat = "PNG";
        }
        
        // Getters and Setters
        public List<String> getSupportedBrowsers() { return supportedBrowsers; }
        public void setSupportedBrowsers(List<String> supportedBrowsers) { this.supportedBrowsers = supportedBrowsers; }
        
        public Map<String, String> getBrowserOptions() { return browserOptions; }
        public void setBrowserOptions(Map<String, String> browserOptions) { this.browserOptions = browserOptions; }
        
        public int getDefaultTimeout() { return defaultTimeout; }
        public void setDefaultTimeout(int defaultTimeout) { this.defaultTimeout = defaultTimeout; }
        
        public boolean isTakeScreenshots() { return takeScreenshots; }
        public void setTakeScreenshots(boolean takeScreenshots) { this.takeScreenshots = takeScreenshots; }
        
        public String getScreenshotFormat() { return screenshotFormat; }
        public void setScreenshotFormat(String screenshotFormat) { this.screenshotFormat = screenshotFormat; }
    }
    
    public static class APIConfig {
        private int connectionTimeout;
        private int readTimeout;
        private boolean enableLogging;
        private String defaultContentType;
        private Map<String, String> defaultHeaders;
        
        public APIConfig() {
            this.connectionTimeout = 10000;
            this.readTimeout = 30000;
            this.enableLogging = true;
            this.defaultContentType = "application/json";
        }
        
        // Getters and Setters
        public int getConnectionTimeout() { return connectionTimeout; }
        public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
        
        public int getReadTimeout() { return readTimeout; }
        public void setReadTimeout(int readTimeout) { this.readTimeout = readTimeout; }
        
        public boolean isEnableLogging() { return enableLogging; }
        public void setEnableLogging(boolean enableLogging) { this.enableLogging = enableLogging; }
        
        public String getDefaultContentType() { return defaultContentType; }
        public void setDefaultContentType(String defaultContentType) { this.defaultContentType = defaultContentType; }
        
        public Map<String, String> getDefaultHeaders() { return defaultHeaders; }
        public void setDefaultHeaders(Map<String, String> defaultHeaders) { this.defaultHeaders = defaultHeaders; }
    }
    
    public static class ReportingConfig {
        private String reportType;
        private boolean generateHtmlReport;
        private boolean generateAllureReport;
        private boolean generateExtentReport;
        private String reportPath;
        
        public ReportingConfig() {
            this.reportType = "all";
            this.generateHtmlReport = true;
            this.generateAllureReport = true;
            this.generateExtentReport = true;
            this.reportPath = "target/reports";
        }
        
        // Getters and Setters
        public String getReportType() { return reportType; }
        public void setReportType(String reportType) { this.reportType = reportType; }
        
        public boolean isGenerateHtmlReport() { return generateHtmlReport; }
        public void setGenerateHtmlReport(boolean generateHtmlReport) { this.generateHtmlReport = generateHtmlReport; }
        
        public boolean isGenerateAllureReport() { return generateAllureReport; }
        public void setGenerateAllureReport(boolean generateAllureReport) { this.generateAllureReport = generateAllureReport; }
        
        public boolean isGenerateExtentReport() { return generateExtentReport; }
        public void setGenerateExtentReport(boolean generateExtentReport) { this.generateExtentReport = generateExtentReport; }
        
        public String getReportPath() { return reportPath; }
        public void setReportPath(String reportPath) { this.reportPath = reportPath; }
    }
    
    public static class DatabaseConfig {
        private String url;
        private String username;
        private String password;
        private String driver;
        private int maxConnections;
        
        public DatabaseConfig() {
            this.maxConnections = 10;
        }
        
        // Getters and Setters
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getDriver() { return driver; }
        public void setDriver(String driver) { this.driver = driver; }
        
        public int getMaxConnections() { return maxConnections; }
        public void setMaxConnections(int maxConnections) { this.maxConnections = maxConnections; }
    }
    
    public static class PerformanceConfig {
        private int threadCount;
        private int rampUpPeriod;
        private boolean enableJMeter;
        private String jmeterPath;
        
        public PerformanceConfig() {
            this.threadCount = 10;
            this.rampUpPeriod = 60;
            this.enableJMeter = false;
        }
        
        // Getters and Setters
        public int getThreadCount() { return threadCount; }
        public void setThreadCount(int threadCount) { this.threadCount = threadCount; }
        
        public int getRampUpPeriod() { return rampUpPeriod; }
        public void setRampUpPeriod(int rampUpPeriod) { this.rampUpPeriod = rampUpPeriod; }
        
        public boolean isEnableJMeter() { return enableJMeter; }
        public void setEnableJMeter(boolean enableJMeter) { this.enableJMeter = enableJMeter; }
        
        public String getJmeterPath() { return jmeterPath; }
        public void setJmeterPath(String jmeterPath) { this.jmeterPath = jmeterPath; }
    }
}
