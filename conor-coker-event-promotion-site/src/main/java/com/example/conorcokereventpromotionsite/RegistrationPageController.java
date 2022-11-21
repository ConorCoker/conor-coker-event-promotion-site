package com.example.conorcokereventpromotionsite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class RegistrationPageController {

    @FXML
    private TextField confirmPasswordTextfield;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField passwordTextfield;

    @FXML
    private TextField phoneTextfield;

    @FXML
    private TextField socialMedia1Textfield;

    @FXML
    private TextField socialMedia2Textfield;

    @FXML
    private TextField websiteTextfield;

    @FXML
    private Label outputLabel;

    private final WhatEventApp whatEventApp = new WhatEventApp();

    private final Utilities utils = new Utilities();

    private static int numberOfUsers = 0;

    public static int getNumberOfUsers() {
        return numberOfUsers;
    }

    @FXML
    void exitButtonOnClick(ActionEvent event) throws IOException {
        utils.switchScreen(event, "whatevent-homepage.fxml", "What Event Ireland - Home");
    }

    @FXML
    void registerButtonOnClick(ActionEvent event) throws IOException {

        if (!passwordsMatch()) {
            utils.clearTextFields(passwordTextfield, confirmPasswordTextfield);
            outputLabel.setText("Passwords Do Not Match!");
        } else if (!requiredFieldsEntered()) {
            outputLabel.setText("Please Enter All Required Fields!");
        } else if (passwordsMatch() && requiredFieldsEntered()) {
            Organiser organiser = new Organiser(nameTextfield.getText(), emailTextfield.getText(), passwordTextfield.getText());
            if (extraFieldsFilled()) {
                organiser.setPhone(phoneTextfield.getText());
                organiser.setWebsite(websiteTextfield.getText());
                organiser.setSocialMedia1(socialMedia1Textfield.getText());
                organiser.setSocialMedia2(socialMedia2Textfield.getText());
            }
            if (whatEventApp.registerUser(emailTextfield.getText(), organiser)) {
                if (!organiser.isAdmin()){
                    numberOfUsers++;
                }
                utils.switchScreen(event, "whatevent-homepage.fxml", "What Event Ireland - Home");
            }
        } else {
            outputLabel.setText("Email " + emailTextfield.getText() + " is already registered in system!");
            utils.clearTextFields(emailTextfield);
        }
    }
    private boolean passwordsMatch() {
        return passwordTextfield.getText().equals(confirmPasswordTextfield.getText());
    }

    private boolean requiredFieldsEntered() {
        return !nameTextfield.getText().isBlank() && !emailTextfield.getText().isBlank()
                && !passwordTextfield.getText().isBlank() && !confirmPasswordTextfield.getText().isBlank();
    }

    private boolean extraFieldsFilled() {
        return !phoneTextfield.getText().isBlank() || !websiteTextfield.getText().isBlank() ||
                !socialMedia1Textfield.getText().isBlank() || !socialMedia2Textfield.getText().isBlank();
    }
}
