package myprojectjava;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterUser {

    public void registerUser() {
        Stage registerStage=new Stage();
        GridPane grid=new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25,25,25,25));
        Text text=new Text("Enter Your Details!!");
        text.setFont(Font.font("Sans",FontWeight.BOLD,18));
        text.setFill(Color.WHITE);
        grid.add(text,0,0,2,1);

        Label name=new Label("Name: ");
        Label username=new Label("Username: ");
        Label contact=new Label("Contact Number: ");
        Label password=new Label("Password: ");
        Label passrepeat=new Label("Re-Type Password: ");

        grid.add(name,0,2);
        grid.add(username,0,3);
        grid.add(contact,0,4);
        grid.add(password,0,5);
        grid.add(passrepeat,0,6);

        TextField nameF=new TextField();
        TextField userF=new TextField();
        TextField contactF=new TextField();
        PasswordField pass=new PasswordField();
        PasswordField repass=new PasswordField();

        grid.add(nameF,1,2);
        grid.add(userF,1,3);
        grid.add(contactF,1,4);
        grid.add(pass,1,5);
        grid.add(repass,1,6);

        HBox hbox2=new HBox();
        Text text2=new Text();
        text2.setStyle("-fx-text-fill:rgba(0,0,0,0);-fx-font-size: 12px;-fx-font-weight: bold;-fx-padding:5px;");
        hbox2.setAlignment(Pos.TOP_CENTER);
        hbox2.getChildren().add(text2);
        grid.add(hbox2,0,1,2,1);

        Button btn=new Button("Register");
        btn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(nameF.getText().isEmpty()) {
                text2.setText("Please enter your name!!");
                text2.setFill(Color.FIREBRICK);
                return;
            }
            if(userF.getText().isEmpty()) {
                    text2.setText("Please enter a username!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
            }
            if(contactF.getText().isEmpty()) {
                    text2.setText("Please enter your Contact Number!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
            }
            if(pass.getText().isEmpty()) {
                    text2.setText("Please enter a password!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
            }
            if(repass.getText().isEmpty()) {
                    text2.setText("Please retype your password!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
            }
            if(!(pass.getText().equals(repass.getText()))) {
                    text2.setText("Passwords entered does not match!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
            }
            if(contactF.getText().length()<10)
            {
                text2.setText("Incorrect Contact Number!!");
                text2.setFill(Color.FIREBRICK);
                return;
            }
            String tmp=contactF.getText();
            if(tmp.length()!=10)
            {
                text2.setText("Enter Contact without +91 or 0 as Prefix");
                text2.setFill(Color.FIREBRICK);
                return;
            }
            for(int i=tmp.length()-1;i>=0;i--)
                if(tmp.charAt(i)<'0'||tmp.charAt(i)>'9')
                {
                    text2.setText("Wrong Contact Number!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
                }
            String name=nameF.getText();
            String user=userF.getText();
            String contact=contactF.getText();
            String password=pass.getText();
            
            Fillo fillo=new Fillo();
            try {
                int chk=0;
                Connection connection=fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/userData.xlsx");
                try {
                    Recordset recordset = connection.executeQuery("Select * from Sheet1 where Username='"+user+"'");
                } catch (FilloException ex) {
                    chk=1;
                }
                if(chk==0)
                {
                    text2.setText("Username not unique!!");
                    text2.setFill(Color.FIREBRICK);
                    return;
                }
                if(chk==1)
                {
                    try {
                    Recordset recordset = connection.executeQuery("Select * from Sheet1 where Contact='"+contact+"'");
                    } catch (FilloException ex) {
                        chk=2;
                    }
                    if(chk==1)
                    {
                        text2.setText("User already Exists!!");
                        text2.setFill(Color.FIREBRICK);
                        return;
                    }
                    else
                    {
                        String strQuery="INSERT INTO Sheet1(Username,Password,Name,Contact,Address,Pincode) VALUES('"+user+"','"+password+"','"+name+"','"+contact+"','null','null')";
                        connection.executeUpdate(strQuery); 
                        connection.close();
                        registerStage.close();
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Welcome Aboard!!");
                        alert.setHeaderText(null);
                        alert.setContentText("Resister Success!");
                        alert.showAndWait();
                    }
                }
                
            } catch (FilloException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
    }});
    HBox hbox=new HBox();
    hbox.getChildren().add(btn);
    hbox.setAlignment(Pos.BOTTOM_CENTER);
    grid.add(hbox,0,7,2,1);
    
    Scene scene = new Scene(grid,400,500);
    scene.getStylesheets().add(MyProjectJava.class.getResource("styleRegisterPage.css").toExternalForm());
    registerStage.setScene(scene);
    registerStage.setResizable(false);
    registerStage.setTitle("Register Yourself");
    registerStage.show();
    }
}
