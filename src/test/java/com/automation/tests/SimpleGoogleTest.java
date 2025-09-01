package com.automation.tests;

import com.automation.core.SimpleBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Simple Google Search Test using the basic framework
 */
public class SimpleGoogleTest extends SimpleBaseTest {
    
    public void testGoogleSearch() {
        try {
            // Initialize test
            initializeTest("testGoogleSearch", "Search for 'Selenium Automation' on Google");
            
            // Navigate to Google
            logStep("Navigate to Google homepage");
            navigateTo("https://www.google.com");
            waitForPageLoad();
            
            // Verify page title
            String title = getPageTitle();
            if (title != null && title.contains("Google")) {
                logStep("Google page loaded successfully");
            } else {
                logStep("Failed to load Google page");
            }
            
            // Find search box
            logStep("Find and interact with search box");
            WebElement searchBox = DriverManager.getWait().until(
                ExpectedConditions.elementToBeClickable(By.name("q"))
            );
            
            // Type search query
            searchBox.clear();
            searchBox.sendKeys("Selenium Automation");
            searchBox.sendKeys(Keys.RETURN);
            
            // Wait for results
            logStep("Wait for search results to load");
            DriverManager.getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.id("search"))
            );
            
            logStep("Search completed successfully");
            
        } catch (Exception e) {
            logStep("Test failed with error: " + e.getMessage());
        } finally {
            // Clean up
            cleanupTest();
        }
    }
    
    @Override
    protected boolean isUITest() {
        return true;
    }
}
