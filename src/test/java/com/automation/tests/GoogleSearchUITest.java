package com.automation.tests;

import com.automation.core.BaseTest;
import com.automation.core.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

/**
 * Sample UI Test demonstrating Google Search functionality
 */
public class GoogleSearchUITest extends BaseTest {
    
    @Test(description = "Search for 'Selenium Automation' on Google and verify results")
    public void testGoogleSearch() {
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
        
        logStep("Enter search term 'Selenium Automation'");
        searchBox.clear();
        searchBox.sendKeys("Selenium Automation");
        
        logStep("Submit search");
        searchBox.sendKeys(Keys.RETURN);
        
        logStep("Wait for search results to load");
        WebDriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.id("search"))
        );
        
        logStep("Verify search results are displayed");
        WebElement searchResults = driver.findElement(By.id("search"));
        assertTrue(searchResults.isDisplayed(), "Search results should be displayed");
        
        logStep("Verify page title contains search term");
        String newPageTitle = getPageTitle();
        assertTrue(newPageTitle.contains("Selenium Automation"), 
            "Page title should contain search term 'Selenium Automation'");
        
        logStep("Verify current URL contains search query");
        String currentUrl = getCurrentUrl();
        assertTrue(currentUrl.contains("q=Selenium+Automation"), 
            "URL should contain search query parameter");
        
        logTestData("Search Term", "Selenium Automation");
        logTestData("Expected Results", "Search results should be displayed");
        logTestData("Actual Results", "Search results are displayed successfully");
    }
    
    @Test(description = "Test Google search suggestions functionality")
    public void testGoogleSearchSuggestions() {
        logStep("Navigate to Google homepage");
        navigateTo("https://www.google.com");
        waitForPageLoad();
        
        logStep("Find search box");
        WebElement searchBox = WebDriverManager.getWait().until(
            ExpectedConditions.elementToBeClickable(By.name("q"))
        );
        
        logStep("Type 'Selenium' to trigger suggestions");
        searchBox.clear();
        searchBox.sendKeys("Selenium");
        
        logStep("Wait for search suggestions to appear");
        WebDriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul[role='listbox']"))
        );
        
        logStep("Verify search suggestions are displayed");
        WebElement suggestions = driver.findElement(By.cssSelector("ul[role='listbox']"));
        assertTrue(suggestions.isDisplayed(), "Search suggestions should be displayed");
        
        logStep("Verify suggestions contain the search term");
        String suggestionsText = suggestions.getText();
        assertTrue(suggestionsText.toLowerCase().contains("selenium"), 
            "Suggestions should contain 'selenium'");
        
        logTestData("Search Term", "Selenium");
        logTestData("Expected Behavior", "Search suggestions should appear");
        logTestData("Actual Behavior", "Search suggestions appeared successfully");
    }
    
    @Test(description = "Test Google search with special characters")
    public void testGoogleSearchWithSpecialCharacters() {
        logStep("Navigate to Google homepage");
        navigateTo("https://www.google.com");
        waitForPageLoad();
        
        logStep("Find search box");
        WebElement searchBox = WebDriverManager.getWait().until(
            ExpectedConditions.elementToBeClickable(By.name("q"))
        );
        
        logStep("Search for term with special characters: 'Selenium + WebDriver'");
        searchBox.clear();
        searchBox.sendKeys("Selenium + WebDriver");
        searchBox.sendKeys(Keys.RETURN);
        
        logStep("Wait for search results to load");
        WebDriverManager.getWait().until(
            ExpectedConditions.presenceOfElementLocated(By.id("search"))
        );
        
        logStep("Verify search results are displayed");
        WebElement searchResults = driver.findElement(By.id("search"));
        assertTrue(searchResults.isDisplayed(), "Search results should be displayed");
        
        logStep("Verify search term is preserved in results");
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Selenium") && pageSource.contains("WebDriver"), 
            "Search results should contain both 'Selenium' and 'WebDriver'");
        
        logTestData("Search Term", "Selenium + WebDriver");
        logTestData("Special Characters", "+ (plus sign)");
        logTestData("Expected Behavior", "Search should handle special characters correctly");
        logTestData("Actual Behavior", "Search handled special characters successfully");
    }
}
