# 🚀 Selenium Hybrid Framework

## 🎯 **UI + REST API Automation Framework**

A comprehensive, enterprise-grade automation framework that combines **Selenium WebDriver** for UI testing and **RestAssured** for API testing, designed for scalable, maintainable, and robust test automation.

### 🌟 **Key Features**

- **🔹 UI Automation**: Selenium WebDriver with advanced WebDriver management
- **🔹 API Testing**: RestAssured with JSON schema validation
- **🔹 Hybrid Testing**: Combine UI and API tests seamlessly
- **🔹 Advanced Reporting**: Extent Reports + Allure Reports
- **🔹 Professional Logging**: Log4j2 with multiple appenders
- **🔹 Configuration Management**: YAML-based framework settings
- **🔹 Parallel Execution**: TestNG parallel test execution
- **🔹 CI/CD Ready**: Jenkins, GitHub Actions integration
- **🔹 Mobile Testing**: Appium integration ready
- **🔹 Database Testing**: MySQL + Testcontainers
- **🔹 Performance Testing**: JMeter integration
- **🔹 Cloud Testing**: Sauce Labs, BrowserStack support

### 🏗️ **Architecture**

```
selenium-hybrid-framework/
├── src/
│   ├── main/java/com/automation/
│   │   ├── api/           # REST API client & utilities
│   │   ├── config/        # Configuration management
│   │   ├── core/          # WebDriver & base test classes
│   │   ├── reporting/     # Extent & Allure reports
│   │   ├── simple/        # Dependency-free demo classes
│   │   └── utils/         # Screenshot & utility classes
│   └── resources/
│       ├── config/        # Environment configurations
│       └── log4j2.xml     # Logging configuration
├── src/test/java/com/automation/tests/
│   ├── GoogleSearchUITest.java    # UI test example
│   ├── RESTAPITest.java           # API test example
│   └── HybridUITest.java          # Hybrid test example
├── pom.xml                        # Maven dependencies
├── testng.xml                     # TestNG configuration
├── run-tests.sh                   # Unix/Linux/Mac execution
├── run-tests.bat                  # Windows execution
└── README.md                      # This file
```

### 🚀 **Quick Start**

#### **Prerequisites**
- Java 17+
- Maven 3.6+
- Chrome/Firefox browser

#### **Installation**
```bash
# Clone the repository
git clone https://github.com/rayush1309/sdet-playground.git
cd sdet-playground/selenium-hybrid-framework

# Install dependencies
mvn clean install

# Run tests
./run-tests.sh ui          # UI tests only
./run-tests.sh api         # API tests only
./run-tests.sh hybrid      # Hybrid tests
./run-tests.sh all         # All tests
```

#### **Quick Demo (No Dependencies)**
```bash
# Run the simplified demo
java -cp src/main/java com.automation.simple.Main
```

### 🧪 **Test Examples**

#### **UI Test**
```java
@Test
public void testGoogleSearch() {
    driver.get("https://www.google.com");
    driver.findElement(By.name("q")).sendKeys("Selenium automation");
    driver.findElement(By.name("btnK")).click();
    // Assertions...
}
```

#### **API Test**
```java
@Test
public void testGetUserAPI() {
    Response response = RestAPIClient.getInstance()
        .get("https://jsonplaceholder.typicode.com/users/1");
    
    response.then()
        .statusCode(200)
        .body("name", equalTo("Leanne Graham"));
}
```

#### **Hybrid Test**
```java
@Test
public void testUIWithAPIValidation() {
    // UI: Navigate to website
    driver.get("https://example.com");
    
    // API: Validate backend data
    Response response = RestAPIClient.getInstance()
        .get("/api/data");
    
    // Combine UI and API assertions
    assertTrue(driver.getTitle().contains("Example"));
    assertEquals(200, response.getStatusCode());
}
```

### 📊 **Reporting**

- **Extent Reports**: Rich HTML reports with screenshots
- **Allure Reports**: Advanced analytics and trends
- **Console Logging**: Real-time test execution logs
- **Screenshot Capture**: Automatic failure screenshots

### ⚙️ **Configuration**

#### **Environment Configuration** (`application-qa.properties`)
```properties
browser=chrome
headless=false
base.url=https://example.com
api.base.url=https://api.example.com
timeout.implicit=10
timeout.explicit=20
```

#### **Framework Configuration** (`framework-config.yml`)
```yaml
ui:
  browser: chrome
  headless: false
  implicitWait: 10
  explicitWait: 20

api:
  baseUrl: https://api.example.com
  readTimeout: 30000
  connectionTimeout: 10000

reporting:
  extent:
    enabled: true
    path: reports/extent/
  allure:
    enabled: true
    path: reports/allure/
```

### 🔧 **Advanced Features**

- **Parallel Execution**: Run tests in parallel across browsers
- **Cross-Browser Testing**: Chrome, Firefox, Safari, Edge
- **Mobile Testing**: Appium integration for mobile apps
- **Database Testing**: MySQL integration with Testcontainers
- **Performance Testing**: JMeter integration
- **Cloud Testing**: Sauce Labs, BrowserStack integration
- **CI/CD Integration**: Jenkins, GitHub Actions, GitLab CI

### 📁 **Project Structure**

This project is part of the **SDET Playground** repository:
- `dsa-practice/` - Data Structures & Algorithms practice
- `selenium-hybrid-framework/` - This automation framework

### 🤝 **Contributing**

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

### 📄 **License**

This project is part of the SDET Playground and is open source.

---

**🎯 Built with ❤️ for the SDET community**
