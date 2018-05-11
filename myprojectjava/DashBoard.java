package myprojectjava;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashBoard implements loggedInUser{
    public void openDashBoard(String username)
    {
        Stage dashStage=new Stage();  
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboard_layout.fxml"));
            Scene scene = new Scene(root,1070,630);
            dashStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        dashStage.setTitle("Dashboard!!");
        dashStage.setResizable(false);
        dashStage.show();
    }
}
