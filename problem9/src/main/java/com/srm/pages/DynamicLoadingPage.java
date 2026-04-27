package com.srm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.srm.config.ConfigReader;

public class DynamicLoadingPage extends BasePage {

    @FindBy(xpath = "//button[normalize-space()='Start']")
    private WebElement startButton;

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Element on page that is hidden')]");
    private final By finishText = By.id("finish");
    private final By loadingIndicator = By.id("loading");

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    public DynamicLoadingPage openExampleOne() {
        driver.get(ConfigReader.getBaseUrl() + "/dynamic-loading/1");
        waitForElement(pageHeader);
        return this;
    } 

    public DynamicLoadingPage startDynamicLoad() {
        click(startButton);
        waitForInvisibility(loadingIndicator);
        waitForElement(finishText);
        return this;
    }

    public String getLoadedText() {
        return waitForElement(finishText).getText().trim();
    }
}