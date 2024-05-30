module com.example.assignmentdraft2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires opencsv;

    opens com.example.assignmentdraft2 to javafx.fxml;
    exports com.example.assignmentdraft2;
}