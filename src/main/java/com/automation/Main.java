package com.automation;

import com.automation.tests.SimpleGoogleTest;

/**
 * Main class to run the automation framework
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("🚀 Starting World-Class Hybrid UI + REST API Automation Framework!");
        System.out.println("=" * 60);
        
        try {
            // Run the simple Google test
            SimpleGoogleTest test = new SimpleGoogleTest();
            test.testGoogleSearch();
            
            System.out.println("✅ Framework test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Framework test failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=" * 60);
        System.out.println("🎯 Framework is ready for advanced testing!");
    }
}
