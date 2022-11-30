module com.example.conorcokereventpromotionsite {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;



    opens com.example.conorcokereventpromotionsite to javafx.fxml;
    exports com.example.conorcokereventpromotionsite;
}