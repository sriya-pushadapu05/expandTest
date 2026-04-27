package com.srm.tests;

import com.srm.config.ConfigReader;
import com.srm.pages.NotesLoginPage;
import com.srm.pages.NotesPage;
import com.srm.pages.RegisterPage;
import com.srm.utils.ExcelUtils;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NotesTest extends BaseTest {

    private String displayName;
    private String email;
    private String password;

    @BeforeClass(alwaysRun = true)
    public void loadRegistrationData() {

        try {
            Object[][] data = ExcelUtils.getSheetData(
                    ConfigReader.getRegisterDataPath(),
                    ConfigReader.getRegisterDataSheet());
            String username = data[0][0].toString().trim();
            password = data[0][1].toString().trim();

            displayName = username + "Ashila";
            email = username + "@gmail.com";

        } catch (Exception e) {
            displayName = "Ashila";
            email = "Ashila@gmail.com";
            password = "Ashila@123";
        }
    }

    @Test(priority = 1)
    public void registerUserForNotesApp() {
        RegisterPage registerPage = new RegisterPage(driver).open();
        registerPage.register(displayName, email, password, password);
    }

    @Test(priority = 2)
    public void createEditDeleteAndFilterNotes() {

        NotesPage notesPage = new NotesLoginPage(driver)
                .open()
                .login(email, password);

        Assert.assertTrue(notesPage.isLoaded(), "Notes page should load after login.");

        String originalTitle = "Note_";
        String updatedTitle = originalTitle + "_Updated";
        String category = "Home";

        notesPage.createNote(originalTitle, "Test description", category);
        Assert.assertTrue(notesPage.isNotePresent(originalTitle));

        notesPage.editNote(originalTitle, updatedTitle, "Updated description");
        Assert.assertTrue(notesPage.isNotePresent(updatedTitle));

        notesPage.filterByCategory(category);
        Assert.assertTrue(notesPage.isNotePresent(updatedTitle));

        notesPage.deleteNote(updatedTitle);
        Assert.assertFalse(notesPage.isNotePresent(updatedTitle));
    }
}