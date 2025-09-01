# 🌟 World-Class Hybrid UI + REST API Automation Framework

A comprehensive, enterprise-grade automation framework that combines the power of **Selenium WebDriver** with **REST API testing** capabilities. This framework is designed for SDETs and QA Engineers who need to test both UI and API layers in a unified, scalable solution.

## 🚀 Key Features

### 🔧 **Core Capabilities**
- **Hybrid Testing**: Seamlessly combine UI and API testing in single test scenarios
- **Multi-Browser Support**: Chrome, Firefox, Edge, Safari with advanced configurations
- **REST API Testing**: Full HTTP method support with RestAssured integration
- **Parallel Execution**: Multi-threaded test execution for faster results
- **Cross-Platform**: Windows, macOS, and Linux support

### 📊 **Advanced Reporting**
- **Extent Reports**: Beautiful HTML reports with screenshots and logs
- **Allure Reports**: Professional-grade reporting with trends and analytics
- **Real-time Logging**: Comprehensive logging with Log4j2
- **Screenshot Management**: Automatic screenshots on failures with cleanup

### 🎯 **Testing Capabilities**
- **UI Automation**: Page Object Model, explicit waits, and smart element handling
- **API Testing**: Request/response validation, JSON schema validation, authentication
- **Database Testing**: MySQL integration with connection pooling
- **Performance Testing**: JMeter integration and performance thresholds
- **Mobile Testing**: Appium support for iOS and Android

### ⚙️ **Enterprise Features**
- **Configuration Management**: Environment-specific configurations
- **Data-Driven Testing**: Excel, JSON, CSV, and database data providers
- **CI/CD Integration**: Jenkins, GitLab, GitHub Actions support
- **Cloud Testing**: Sauce Labs, BrowserStack, LambdaTest integration
- **Security Testing**: SSL verification, proxy support, data encryption

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    Hybrid Automation Framework              │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   UI Tests  │  │  API Tests  │  │ Hybrid Tests│        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │  Selenium   │  │RestAssured  │  │ TestNG      │        │
│  │ WebDriver   │  │             │  │             │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Extent    │  │   Allure    │  │   Log4j2    │        │
│  │  Reports    │  │  Reports    │  │             │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
└─────────────────────────────────────────────────────────────┘
```

## 📁 Project Structure

```
selenium-automation-framework/
├── src/
│   ├── main/java/com/automation/
│   │   ├── core/                    # Core framework classes
│   │   │   ├── BaseTest.java        # Base test class
│   │   │   └── WebDriverManager.java # WebDriver management
│   │   ├── api/                     # API testing components
│   │   │   └── RestAPIClient.java   # REST API client
│   │   ├── config/                  # Configuration management
│   │   │   ├── ConfigManager.java   # Configuration loader
│   │   │   └── FrameworkConfig.java # Framework settings
│   │   ├── reporting/               # Reporting components
│   │   │   └── ExtentReportManager.java
│   │   └── utils/                   # Utility classes
│   │       └── ScreenshotUtils.java
│   ├── main/resources/
│   │   └── config/                  # Configuration files
│   │       ├── application-qa.properties
│   │       └── framework-config.yml
│   └── test/java/com/automation/
│       └── tests/                   # Test classes
│           ├── GoogleSearchUITest.java
│           ├── RESTAPITest.java
│           └── HybridUITest.java
├── testng.xml                       # TestNG configuration
├── pom.xml                          # Maven dependencies
└── README.md                        # This file
```

## 🛠️ Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: 3.6.0 or higher
- **Browsers**: Chrome, Firefox, Edge, Safari
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code

## 📦 Installation & Setup

### 1. **Clone the Repository**
```bash
git clone https://github.com/yourusername/selenium-automation-framework.git
cd selenium-automation-framework
```

### 2. **Install Dependencies**
```bash
mvn clean install
```

### 3. **Configure Environment**
- Copy `src/main/resources/config/application-qa.properties` to your environment
- Update URLs, credentials, and paths as needed
- Modify `src/main/resources/config/framework-config.yml` for framework settings

### 4. **Run Tests**
```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -DsuiteXmlFile=testng.xml

# Run with specific browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true

# Run specific test group
mvn test -Dgroups=smoke
```

## 🧪 Test Examples

### **UI Test Example**
```java
@Test(description = "Search on Google and verify results")
public void testGoogleSearch() {
    logStep("Navigate to Google homepage");
    navigateTo("https://www.google.com");
    waitForPageLoad();
    
    logStep("Find and interact with search box");
    WebElement searchBox = WebDriverManager.getWait().until(
        ExpectedConditions.elementToBeClickable(By.name("q"))
    );
    
    searchBox.sendKeys("Selenium Automation");
    searchBox.sendKeys(Keys.RETURN);
    
    logStep("Verify search results");
    WebElement results = driver.findElement(By.id("search"));
    assertTrue(results.isDisplayed(), "Search results should be displayed");
}
```

### **API Test Example**
```java
@Test(description = "Test REST API endpoint")
public void testGetPosts() {
    logStep("Send GET request to /posts endpoint");
    Response response = getApiClient().get("/posts");
    
    logStep("Validate response");
    assertTrue(getApiClient().validateStatusCode(response, 200));
    
    int postCount = response.jsonPath().getList("$").size();
    assertEquals(postCount, 100, "Should return 100 posts");
}
```

### **Hybrid Test Example**
```java
@Test(description = "UI + API combined test")
public void testHybridScenario() {
    // Phase 1: UI Testing
    navigateTo("https://example.com");
    // ... UI interactions
    
    // Phase 2: API Validation
    Response response = getApiClient().get("/api/data");
    // ... API validation
    
    // Phase 3: Data Correlation
    // ... Correlate UI and API data
}
```

## 🔧 Configuration

### **Environment Configuration**
```properties
# application-qa.properties
browser=chrome
headless=false
base.url=https://www.google.com
api.base.url=https://api.example.com
implicit.wait=10
explicit.wait=20
```

### **Framework Configuration**
```yaml
# framework-config.yml
ui:
  supportedBrowsers: [chrome, firefox, edge, safari]
  defaultTimeout: 30
  takeScreenshots: true

api:
  connectionTimeout: 10000
  readTimeout: 30000
  enableLogging: true

reporting:
  reportType: "all"
  generateHtmlReport: true
  generateAllureReport: true
```

## 📊 Reporting

### **Extent Reports**
- Beautiful HTML reports with dark theme
- Screenshot attachments on failures
- Test step logging and data tracking
- Real-time test execution status

### **Allure Reports**
- Professional-grade reporting
- Trend analysis and metrics
- Environment information
- Test execution history

### **Logging**
- Structured logging with Log4j2
- Console and file output
- Different log levels (INFO, DEBUG, ERROR)
- Performance metrics logging

## 🚀 Advanced Features

### **Parallel Execution**
```xml
<!-- testng.xml -->
<suite name="Parallel Suite" parallel="methods" thread-count="3">
    <test name="Parallel Tests">
        <classes>
            <class name="com.automation.tests.ParallelTest"/>
        </classes>
    </test>
</suite>
```

### **Data-Driven Testing**
```java
@DataProvider(name = "testData")
public Object[][] getTestData() {
    return new Object[][] {
        {"user1", "pass1"},
        {"user2", "pass2"}
    };
}

@Test(dataProvider = "testData")
public void testLogin(String username, String password) {
    // Test implementation
}
```

### **Page Object Model**
```java
public class LoginPage {
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    public void login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }
}
```

## 🔒 Security Features

- **SSL Verification**: Configurable SSL certificate validation
- **Proxy Support**: HTTP/HTTPS proxy configuration
- **Data Encryption**: Sensitive data encryption options
- **Authentication**: Bearer token, Basic auth, OAuth2 support
- **Secure Headers**: Configurable security headers

## 🌐 Cloud Integration

### **Sauce Labs**
```yaml
cloud:
  provider: "saucelabs"
  saucelabs:
    username: "your-username"
    accessKey: "your-access-key"
    region: "us-west-1"
```

### **BrowserStack**
```yaml
cloud:
  provider: "browserstack"
  browserstack:
    username: "your-username"
    accessKey: "your-access-key"
    local: false
```

## 📱 Mobile Testing

### **iOS Configuration**
```yaml
mobile:
  platform: "ios"
  ios:
    deviceName: "iPhone 12"
    platformVersion: "15.0"
    automationName: "XCUITest"
```

### **Android Configuration**
```yaml
mobile:
  platform: "android"
  android:
    deviceName: "Pixel 5"
    platformVersion: "11.0"
    automationName: "UiAutomator2"
```

## 🔄 CI/CD Integration

### **Jenkins Pipeline**
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/reports',
                    reportFiles: 'index.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
}
```

### **GitHub Actions**
```yaml
name: Automation Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run Tests
        run: mvn test
      - name: Upload Reports
        uses: actions/upload-artifact@v2
        with:
          name: test-reports
          path: target/reports/
```

## 📈 Performance Testing

### **JMeter Integration**
```yaml
performance:
  enableJMeter: true
  jmeterPath: "/usr/local/bin/jmeter"
  loadTest:
    enabled: true
    duration: 300
    rampUp: 60
    holdTime: 180
    rampDown: 60
```

### **Performance Thresholds**
```yaml
thresholds:
  ui:
    pageLoadTime: 10000
    elementWaitTime: 5000
  api:
    responseTime: 5000
    throughput: 100
```

## 🗄️ Database Testing

### **MySQL Integration**
```yaml
database:
  url: "jdbc:mysql://localhost:3306/testdb"
  username: "testuser"
  password: "testpass"
  driver: "com.mysql.cj.jdbc.Driver"
  maxConnections: 10
```

### **Test Data Management**
```yaml
testData:
  cleanupAfterTest: true
  backupBeforeTest: false
  restoreAfterTest: true
```

## 🧪 Test Categories

- **Smoke Tests**: Critical functionality validation
- **Regression Tests**: Comprehensive feature testing
- **UI Tests**: User interface automation
- **API Tests**: REST API validation
- **Hybrid Tests**: Combined UI + API testing
- **Performance Tests**: Load and stress testing
- **Security Tests**: Vulnerability assessment
- **Mobile Tests**: Mobile application testing

## 📚 Best Practices

### **Test Design**
1. **Single Responsibility**: Each test should test one specific functionality
2. **Data Independence**: Tests should not depend on each other
3. **Clean Setup/Teardown**: Proper resource management
4. **Meaningful Assertions**: Clear validation messages
5. **Page Object Model**: Separate test logic from page interactions

### **Framework Usage**
1. **Configuration Management**: Use environment-specific configs
2. **Logging**: Comprehensive logging for debugging
3. **Screenshots**: Automatic screenshots on failures
4. **Parallel Execution**: Utilize parallel execution for faster results
5. **Reporting**: Generate detailed reports for stakeholders

### **Maintenance**
1. **Regular Updates**: Keep dependencies updated
2. **Code Review**: Regular code review and refactoring
3. **Documentation**: Maintain up-to-date documentation
4. **Monitoring**: Monitor test execution and performance
5. **Backup**: Regular backup of test data and configurations

## 🐛 Troubleshooting

### **Common Issues**

#### **WebDriver Issues**
```bash
# Update WebDriver versions
mvn clean install -U

# Check browser compatibility
# Verify WebDriver path in configuration
```

#### **API Testing Issues**
```bash
# Check network connectivity
# Verify API endpoints
# Check authentication tokens
# Review request/response logs
```

#### **Performance Issues**
```bash
# Reduce thread count
# Increase timeouts
# Check system resources
# Review test data size
```

### **Debug Mode**
```bash
# Enable debug logging
mvn test -Dlog.level=DEBUG

# Run single test
mvn test -Dtest=GoogleSearchUITest#testGoogleSearch

# Run with specific parameters
mvn test -Dbrowser=firefox -Dheadless=true
```

## 📞 Support & Community

### **Documentation**
- [Framework Documentation](docs/)
- [API Reference](docs/api/)
- [Examples](docs/examples/)
- [Troubleshooting Guide](docs/troubleshooting/)

### **Contributing**
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

### **Issues & Questions**
- [GitHub Issues](https://github.com/yourusername/selenium-automation-framework/issues)
- [Discussions](https://github.com/yourusername/selenium-automation-framework/discussions)
- [Wiki](https://github.com/yourusername/selenium-automation-framework/wiki)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Selenium WebDriver** team for the excellent automation framework
- **RestAssured** team for API testing capabilities
- **TestNG** team for the testing framework
- **Extent Reports** team for beautiful reporting
- **Allure Framework** team for professional reporting
- **Apache Maven** team for build management

## 🎯 Roadmap

### **Version 2.0 (Q2 2024)**
- [ ] AI-powered test generation
- [ ] Visual testing integration
- [ ] Advanced performance analytics
- [ ] Cloud-native architecture

### **Version 1.5 (Q1 2024)**
- [ ] Enhanced mobile testing
- [ ] GraphQL API support
- [ ] Advanced security testing
- [ ] Machine learning integration

### **Version 1.2 (Q4 2023)**
- [ ] Additional browser support
- [ ] Enhanced reporting
- [ ] Performance improvements
- [ ] Bug fixes and stability

---

**Built with ❤️ for the QA Community**

*This framework represents the future of automation testing - combining the best of UI and API testing in a single, powerful solution.*
