package com.srm.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.srm.pages.CheckboxPage;
import com.srm.pages.DropdownPage;
import com.srm.pages.InputsPage;
import com.srm.pages.RadioButtonPage;

public class ForminteractionTest extends BaseTest {

    @Test
    public void verifyInputsAcceptValues() {
        InputsPage inputsPage = new InputsPage(driver)
                .open()
                .enterInputValues("23", "ExpandTesting", "SafePassword123!", "2026-04-24")
                .clickDisplayInputs();

        Assert.assertEquals(inputsPage.getNumberValue(), "23", "Number should retain the entered value");
        Assert.assertEquals(inputsPage.getTextValue(), "ExpandTesting", "Text should retain the entered value");
        Assert.assertEquals(inputsPage.getPasswordValue(), "SafePassword123!", "Password should retain the entered value.");
        Assert.assertEquals(inputsPage.getDateValue(), "2026-04-24", "Date should retain the entered value.");
        Assert.assertTrue(inputsPage.areInputsDisplayed(), "Display summary should appear after submission.");
    }

    @Test
    public void verifyDropdownSelection() {
        DropdownPage dropdownPage = new DropdownPage(driver)
                .open()
                .selectOption("Option 1");

        Assert.assertEquals(dropdownPage.getSelectedOption(), "Option 1",
                "Selected dropdown option should match the chosen value.");
    } 

    @Test
    public void verifyCheckboxInteraction() {
        CheckboxPage checkboxPage = new CheckboxPage(driver).open();
        boolean initialState = checkboxPage.isFirstCheckboxSelected();

        checkboxPage.toggleFirstCheckbox();
        Assert.assertNotEquals(checkboxPage.isFirstCheckboxSelected(), initialState,
                "First checkbox state should change after the first toggle.");

        checkboxPage.toggleFirstCheckbox();
        Assert.assertEquals(checkboxPage.isFirstCheckboxSelected(), initialState,
                "First checkbox should return to its original state after the second toggle.");
    }

    @Test
    public void verifyRadioButtonSingleSelection() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(driver).open();

        radioButtonPage.selectFirstRadioButton();
        Assert.assertTrue(radioButtonPage.isFirstSelected(), "First radio button should be selected.");

        radioButtonPage.selectSecondRadioButton();
        Assert.assertTrue(radioButtonPage.isSecondSelected(), "Second radio button should be selected.");
        Assert.assertFalse(radioButtonPage.isFirstSelected(),
                "Only one radio button should remain selected at a time.");
    }
}