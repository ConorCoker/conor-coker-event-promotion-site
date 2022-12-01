package com.example.conorcokereventpromotionsite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WhatEventHomepageController implements Initializable {

    @FXML
    private ChoiceBox<String> eventCategoryChoiceBox;

    @FXML
    private ChoiceBox<String> eventLocationChoiceBox;

    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Event, Image> imageTableColumn;

    @FXML
    private TableColumn<Event, String> nameTableColumn;

    @FXML
    private TableColumn<Event, String> typeTableColumn;

    @FXML
    private TableColumn<Event, String> locationTableColumn;

    @FXML
    private TableColumn<Event, String> priceTableColumn;

    @FXML
    private TableColumn<Event, String> dateTableColumn;

    @FXML
    private Text textOutput;

    @FXML
    private Button buttonSave;

    private ObservableList<Event> observableListOfEvents;

    private final ObservableList<Event> emptyListToClearTableBeforeRefilling = FXCollections.observableArrayList();

    private final Utilities utils = new Utilities();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableListOfEvents = FXCollections.observableArrayList(WhatEventApp.getEvents());
        textOutput.setText("Number of Organisers Registered = " + WhatEventApp.getUsers().size());
        fillCheckboxes();
        setupTable();
    }

    private void fillCheckboxes() {
        ObservableList<String> areas = FXCollections.observableArrayList("Any Area", "Waterford", "Kilkenny", "Galway", "Dublin", "Limerick");
        eventLocationChoiceBox.setItems(areas);
        eventLocationChoiceBox.setValue("Any Area");
        ObservableList<String> types = FXCollections.observableArrayList("Any Category", "Music", "Drama", "Poetry", "Comedy", "Entertainment", "Educational");
        eventCategoryChoiceBox.setItems(types);
        eventCategoryChoiceBox.setValue("Any Category");
    }

    private void setupTable() {

        imageTableColumn.setCellFactory(param -> {
            ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            TableCell<Event, Image> cell = new TableCell<>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageView.setImage(item);
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        imageTableColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(observableListOfEvents);
    }

    @FXML
    void loginButtonOnClick(ActionEvent event) throws IOException {
        utils.switchScreen(event, "whatevent-login-page.fxml", "What Event Ireland - Login");
    }

    @FXML
    void registerButtonOnClick(ActionEvent event) throws IOException {

        utils.switchScreen(event, "whatevent-registration-page.fxml", "What Event Ireland - Registration");
    }

    @FXML
    void buttonFindOnClick() {


        tableView.setItems(emptyListToClearTableBeforeRefilling);
        if (eventCategoryChoiceBox.getValue().equals("Any Category") && !eventLocationChoiceBox.getValue().equals("Any Area")) {
            filterEventsByLocation(eventLocationChoiceBox.getValue());
        } else if (!eventCategoryChoiceBox.getValue().equals("Any Category") && eventLocationChoiceBox.getValue().equals("Any Area")) {
            filterEventsByCategory(eventCategoryChoiceBox.getValue());
        } else if (!eventLocationChoiceBox.getValue().equals("Any Area") && !eventCategoryChoiceBox.getValue().equals("Any Category")) {
            filterEventsByCategoryAndLocation(eventCategoryChoiceBox.getValue(), eventLocationChoiceBox.getValue());
        } else {
            observableListOfEvents = FXCollections.observableArrayList(WhatEventApp.getEvents());
        }
        setupTable();
    }

    @FXML
    void buttonSaveOnClick() {
        try{
            WhatEventApp.save();
        }catch (Exception e){
            textOutput.setText(e+" error has prevented us from saving!");
        }
    }

    @FXML
    void buttonLoadOnClick(){
        try {
            WhatEventApp.load();
            repopulateImages();
            textOutput.setText("Number of Organisers Registered = " + WhatEventApp.getUsers().size());
            buttonSave.setDisable(false);


        }catch (Exception e){
            textOutput.setText(e+" error has occurred!");
        }

    }

    @FXML
    void mouseClickedOnTableListener(MouseEvent mouseEvent) throws IOException {
        handleTableClicks(mouseEvent);
    }

    private void filterEventsByCategoryAndLocation(String category, String location) {

        ObservableList<Event> eventsMatchingFilter = FXCollections.observableArrayList();

        for (Event event : WhatEventApp.getEvents()) {

            if (event.getType().equals(category) && event.getLocation().equals(location)) {
                eventsMatchingFilter.add(event);
            }
        }
        observableListOfEvents = eventsMatchingFilter;
    }

    private void filterEventsByCategory(String category) {

        ObservableList<Event> eventsMatchingFilter = FXCollections.observableArrayList();

        for (Event event : WhatEventApp.getEvents()) {

            if (event.getType().equals(category)) {
                eventsMatchingFilter.add(event);
            }


        }
        observableListOfEvents = eventsMatchingFilter;

    }

    private void filterEventsByLocation(String location) {

        ObservableList<Event> eventsMatchingFilter = FXCollections.observableArrayList();

        for (Event event : WhatEventApp.getEvents()) {

            if (event.getLocation().equals(location)) {
                eventsMatchingFilter.add(event);
            }
        }
        observableListOfEvents = eventsMatchingFilter;
    }

    private void repopulateImages() {
        for (Event event : WhatEventApp.getEvents()) {
          if (!event.getImageFileLocation().equals("no image")){
              event.setImage(new Image(event.getImageFileLocation()));

          }
        }

    }

    private void handleTableClicks(MouseEvent mouseEvent) throws IOException {

        if (tableView.getSelectionModel().getSelectedItem()!=null){
            tableView.getSelectionModel().getSelectedItem().setInDepthView(true);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(WhatEventApp.class.getResource("whatevent-event-details-indepth.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("More Details");
            stage.setScene(scene);
            stage.show();
        }
    }


}