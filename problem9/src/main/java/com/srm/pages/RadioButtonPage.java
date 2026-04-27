package com.srm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.srm.config.ConfigReader;

public class RadioButtonPage extends BasePage {

    @FindBy(xpath = "(//input[@type='radio'])[1]")
    private WebElement firstRadioButton;

    @FindBy(xpath = "(//input[@type='radio'])[2]")
    private WebElement secondRadioButton;

    private final By pageHeader = By.xpath("//h1[contains(normalize-space(),'Radio Buttons')]");

    public RadioButtonPage(WebDriver driver) {
        super(driver);
    }

    public RadioButtonPage open() {
        driver.get(ConfigReader.getBaseUrl() + "/radio-buttons");
        waitForElement(pageHeader);
        return this;
    } 

    public RadioButtonPage selectFirstRadioButton() {
        jsClick(firstRadioButton);
        return this;
    }

    public RadioButtonPage selectSecondRadioButton() {
        jsClick(secondRadioButton);
        return this;
    }

    public boolean isFirstSelected() {
        return firstRadioButton.isSelected();
    }

    public boolean isSecondSelected() {
        return secondRadioButton.isSelected();
    }
}