package com.example.conorcokereventpromotionsite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private Text textFeedback;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldEmail;

    private final Utilities utils = new Utilities();

    @FXML
    void buttonBackOnClick(ActionEvent event) throws IOException {

        utils.switchScreen(event, "whatevent-homepage.fxml", "What Event Ireland - Home");
    }

    @FXML
    void buttonClearOnAction() {

        utils.clearTextFields(textFieldEmail, textFieldPassword);
    }

    @FXML
    void buttonLoginOnAction(ActionEvent event) throws IOException {

        if (isRegisteredAndIsPasswordCorrect(textFieldEmail.getText(), textFieldPassword.getText())) {
            Organiser userAccount = WhatEventApp.getUsers().get(textFieldEmail.getText());
            if (userAccount.isEnabled()) {
                if (userAccount.isAdmin()) {
                    utils.switchScreen(event, "whatevent-admin-page.fxml", "You are logged in as an administrator!");
                } else {
                    utils.switchScreen(event, "whatevent-organiser-page.fxml", "You are logged into What Event Ireland!");
                }
            } else {
                textFeedback.setText("Your account has been disabled!");
            }
        } else {
            textFeedback.setText("Your Email or Password is Incorrect!");
            utils.clearTextFields(textFieldEmail, textFieldPassword);
        }
    }

    private boolean isRegisteredAndIsPasswordCorrect(String email, String password) {
        if (WhatEventApp.getUsers().containsKey(email)) {
            Organiser userAccount = WhatEventApp.getUsers().get(email);
            if (password.equals(userAccount.getPassword())) {
                WhatEventApp.setLoggedInUser(userAccount);
                return true;
            }
            return false;
        }
        return false;
    }
}


