package com.automation.utils;

import com.automation.config.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Screenshot utility class for the Hybrid Automation Framework
 */
public class ScreenshotUtils {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    /**
     * Take screenshot and save to file
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            if (driver instanceof TakesScreenshot) {
                String timestamp = LocalDateTime.now().format(formatter);
                String fileName = String.format("%s_%s.png", testName, timestamp);
                String screenshotPath = getScreenshotPath(fileName);
                
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(screenshotPath));
                
                logger.info("Screenshot saved: {}", screenshotPath);
                return screenshotPath;
            } else {
                logger.warn("WebDriver does not support taking screenshots");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }
    
    /**
     * Take screenshot with custom filename
     */
    public static String takeScreenshot(WebDriver driver, String testName, String customName) {
        try {
            if (driver instanceof TakesScreenshot) {
                String timestamp = LocalDateTime.now().format(formatter);
                String fileName = String.format("%s_%s_%s.png", testName, customName, timestamp);
                String screenshotPath = getScreenshotPath(fileName);
                
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(screenshotPath));
                
                logger.info("Screenshot saved: {}", screenshotPath);
                return screenshotPath;
            } else {
                logger.warn("WebDriver does not support taking screenshots");
                return null;
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }
    
    /**
     * Take screenshot as bytes
     */
    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        try {
            if (driver instanceof TakesScreenshot) {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } else {
                logger.warn("WebDriver does not support taking screenshots");
                return new byte[0];
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot as bytes", e);
            return new byte[0];
        }
    }
    
    /**
     * Get screenshot as bytes from file path
     */
    public static byte[] getScreenshotAsBytes(String screenshotPath) {
        try {
            Path path = Paths.get(screenshotPath);
            if (Files.exists(path)) {
                return Files.readAllBytes(path);
            } else {
                logger.warn("Screenshot file not found: {}", screenshotPath);
                return new byte[0];
            }
        } catch (IOException e) {
            logger.error("Failed to read screenshot file: {}", screenshotPath, e);
            return new byte[0];
        }
    }
    
    /**
     * Get screenshot directory path
     */
    private static String getScreenshotPath(String fileName) {
        String basePath = ConfigManager.getInstance().getProperty("screenshot.path", "target/screenshots");
        File directory = new File(basePath);
        
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        return Paths.get(basePath, fileName).toString();
    }
    
    /**
     * Clean old screenshots (older than specified days)
     */
    public static void cleanOldScreenshots(int daysToKeep) {
        try {
            String basePath = ConfigManager.getInstance().getProperty("screenshot.path", "target/screenshots");
            File directory = new File(basePath);
            
            if (directory.exists()) {
                long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24 * 60 * 60 * 1000L);
                
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.lastModified() < cutoffTime) {
                            if (file.delete()) {
                                logger.info("Deleted old screenshot: {}", file.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to clean old screenshots", e);
        }
    }
    
    /**
     * Get screenshot count in directory
     */
    public static int getScreenshotCount() {
        try {
            String basePath = ConfigManager.getInstance().getProperty("screenshot.path", "target/screenshots");
            File directory = new File(basePath);
            
            if (directory.exists()) {
                File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
                return files != null ? files.length : 0;
            }
            return 0;
        } catch (Exception e) {
            logger.error("Failed to get screenshot count", e);
            return 0;
        }
    }
    
    /**
     * Get screenshot directory size in MB
     */
    public static double getScreenshotDirectorySizeMB() {
        try {
            String basePath = ConfigManager.getInstance().getProperty("screenshot.path", "target/screenshots");
            File directory = new File(basePath);
            
            if (directory.exists()) {
                long size = calculateDirectorySize(directory);
                return size / (1024.0 * 1024.0); // Convert to MB
            }
            return 0.0;
        } catch (Exception e) {
            logger.error("Failed to get screenshot directory size", e);
            return 0.0;
        }
    }
    
    /**
     * Calculate directory size recursively
     */
    private static long calculateDirectorySize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else if (file.isDirectory()) {
                    size += calculateDirectorySize(file);
                }
            }
        }
        
        return size;
    }
}
