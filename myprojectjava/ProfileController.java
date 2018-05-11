/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectjava;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static myprojectjava.loggedInUser.activeUserName;

public class ProfileController{

    @FXML Text nameText;
    @FXML Text usernameText;
    @FXML Text contactText;
    @FXML TextField oldPassword;
    @FXML TextField newPassword;
    @FXML TextField reTypePassword;
    @FXML Button saveButton;
    @FXML Text errorText;
    
    public void initialize() {
        String srch=activeUserName.get(0);
        Fillo fillo=new Fillo();
        try {
            Connection connection2 = fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/userData.xlsx");
            Recordset recordset = connection2.executeQuery("Select * from Sheet1 where Username='"+srch+"'");
            recordset.next();
            String username=recordset.getField("Username");
            String name=recordset.getField("Name");
            String contact=recordset.getField("Contact");
            String password=recordset.getField("Password");
            nameText.setText(name);
            usernameText.setText(username);
            contactText.setText(contact);
            saveButton.setOnAction(e->{
            if(oldPassword.getText().equals(password))
            {
                if(newPassword.getText().equals(reTypePassword.getText()))
                {
                    try {
                        connection2.executeUpdate("Update Sheet1 Set Password="+"'"+newPassword.getText()+"'"+" where Username="+"'"+activeUserName.get(0)+"'");
                    } catch (FilloException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    errorText.setText("Password Changed Successfully!!");
                    oldPassword.setText("");
                    newPassword.setText("");
                    reTypePassword.setText("");
                }
                else
                    errorText.setText("Entered Passwords Dont Match!!");
            }
            else
                errorText.setText("Wrong Password Entered!!");
            
            });
            
        }catch (FilloException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    
    
}
