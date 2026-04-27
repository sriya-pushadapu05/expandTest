# Test Framework Design

## Framework Type

Page Object Model (POM)

---

## Features

* Config-driven execution using `config.properties`
* Data-driven testing using Excel (DataProvider)
* Reusable Page methods using POM design
* FluentWait / WebDriverWait for dynamic elements
* Screenshot capture on failure
* ExtentReports for HTML reporting
* Unique test data handling to avoid duplicates

---

## Test Coverage

* User Registration
* Login (valid & invalid scenarios)
* Create Note
* Edit Note
* Delete Note
* Filter Notes by Category
* Search Notes
* Form Validations

---
## Project Structure
```
ExpandTest
│
├── src/main/java
│   ├── com.srm.config
│   │   └── ConfigReader.java
│   │
│   ├── com.srm.driver
│   │   └── DriverFactory.java
│   │
│   ├── com.srm.listeners
│   │   └── TestListener.java
│   │
│   ├── com.srm.pages
│   │   ├── BasePage.java
│   │   ├── AuthLoginPage.java
│   │   ├── SecureAreaPage.java
│   │   ├── RegisterPage.java
│   │   ├── NotesLoginPage.java
│   │   ├── NotesPage.java
│   │   ├── InputsPage.java
│   │   ├── DropdownPage.java
│   │   ├── CheckboxPage.java
│   │   ├── RadioButtonPage.java
│   │   ├── DragAndDropCirclesPage.java
│   │   ├── DynamicLoadingPage.java
│   │   └── AlertPage.java
│   │
│   ├── com.srm.utils
│   │   ├── ExcelUtils.java
│   │   ├── ExtentManager.java
│   │   ├── ScreenshotUtils.java
│   │   └── WaitUtils.java
│
├── src/test/resources
│   ├── config.properties
│   └── testdata6.xlsx
│
├── src/test/java
│   └── com.srm.tests
│       ├── BaseTest.java
│       ├── AuthTest.java
│       ├── NotesTest.java
│       ├── FormInteractionTest.java
│       ├── DynamicElementsTest.java
│       └── AlertTest.java
│
├── reports/           
├── screenshots/       
│
├── testng.xml          
├── pom.xml             
```
---
## Tools & Technologies

* Java
* Selenium WebDriver
* TestNG
* Maven
* WebDriverManager
* ExtentReports

---

## Framework Highlights

* Modular and reusable design using Page Object Model
* Separation of test logic and UI locators
* Robust wait handling for dynamic UI (React-based app)
* Handles real-world scenarios like duplicate users and multiple notes
* Scalable structure for adding new test modules

---
