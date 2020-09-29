import connection.ConnectionClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        boolean isConnected = true;

        try {
            ConnectionClass connectionClass = new ConnectionClass();
        } catch (SQLException e) {
            isConnected = false;

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Communications Link Failure");
            alert.setContentText("Please make sure database is online");
            alert.showAndWait();
        }

        if (isConnected) {
            Parent root = FXMLLoader.load(getClass().getResource("views/LoginPage.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } else {
            return;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
