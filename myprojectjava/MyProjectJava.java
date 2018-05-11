/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectjava;
import java.io.FileInputStream;
import java.util.EventListener;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.ImageIcon;
/**
 *
 * @author Gaurav Jindal
 */
public class MyProjectJava extends Application implements loggedInUser{
    
    private String username;
    private String password;
    
    @Override
    public void start(Stage primaryStage) {

        
        GridPane grid=new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25,25,25,25));
        
        final Text loginCheck=new Text();
        loginCheck.setStyle("-fx-text-fill:rgba(0,0,0,0);-fx-font-size: 12px;-fx-font-weight: bold;");
        HBox hboxLogin=new HBox();
        hboxLogin.setAlignment(Pos.BOTTOM_RIGHT);
        hboxLogin.getChildren().add(loginCheck);
        grid.add(hboxLogin,0,6,2,1);
        
        Text txt = new Text("Please Login!!");
        txt.setFill(Color.WHITE);
        txt.setFont(Font.font("Arial",FontWeight.BOLD,18));
        grid.add(txt,0,0,2,1);
        
        Label userLabel=new Label("Username:");
        grid.add(userLabel,0,1);
        TextField userText=new TextField();
        grid.add(userText,1,1);
        
        Label passLabel=new Label("Password:");
        grid.add(passLabel,0,2);
        PasswordField passText=new PasswordField();
        grid.add(passText,1,2);
        
        Button btn=new Button("Sign in");
        
        btn.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Sign in button pressed");
            if(userText.getText().isEmpty())
            {
                loginCheck.setText("Please Enter Username!!");
                loginCheck.setFill(Color.FIREBRICK);
                return;
            }
            if(passText.getText().isEmpty())
            {
                loginCheck.setText("Please Enter Password!!");
                loginCheck.setFill(Color.FIREBRICK);
                return;
            }
            username=userText.getText();
            password=passText.getText();
            LoginVerify login= new LoginVerify();
            int verify=login.verifyUser(username,password);
            if(verify==1){
                activeUserName.add(username);
                primaryStage.close();
                DashBoard dash=new DashBoard();
                dash.openDashBoard(username);
            }   
            else
            {
                loginCheck.setText("Please check user Credentials!!");
                loginCheck.setFill(Color.FIREBRICK);
            }   
        }
        });
        HBox hbox=new HBox();
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(btn);
        grid.add(hbox,1,4);
       
        Button register=new Button("Dont have an account?");
        register.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                RegisterUser newUser=new RegisterUser();
                newUser.registerUser();
            }    
    });
        register.setId("register-button");
        HBox hbox2=new HBox();
        hbox2.setAlignment(Pos.BOTTOM_RIGHT);
        hbox2.getChildren().add(register);
        grid.add(hbox2,1,5);
        
        Scene scene = new Scene(grid, 400, 300);    
        scene.getStylesheets().add(MyProjectJava.class.getResource("styleLoginPage.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome!!");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
