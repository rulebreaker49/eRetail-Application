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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import static myprojectjava.loggedInUser.activeUserName;


public class AddressController{

    @FXML Text errorText;
    @FXML TextArea textArea;
    @FXML TextField pincodeField;
    @FXML Button updateButton;
    
    public void initialize(){
       String srch=activeUserName.get(0);
        Fillo fillo=new Fillo();
        try {
            Connection connection2 = fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/userData.xlsx");
            Recordset recordset = connection2.executeQuery("Select * from Sheet1 where Username='"+srch+"'");
            recordset.next();
            String address=recordset.getField("Address");
            String pincode=recordset.getField("Pincode");
            if(!address.equals("null"))
                textArea.setText(address);
            if(!pincode.equals("null"))
                pincodeField.setText(pincode);
            updateButton.setOnAction(e->{
            if(!textArea.getText().equals(""))
            {
                if(!pincodeField.getText().equals(""))
                {
                    try {
                        connection2.executeUpdate("Update Sheet1 Set Address="+"'"+textArea.getText()+"'"+" where Username="+"'"+srch+"'");
                        connection2.executeUpdate("Update Sheet1 Set Pincode="+"'"+pincodeField.getText()+"'"+" where Username="+"'"+srch+"'");
                    } catch (FilloException ex) {
                        Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    errorText.setText("Address Saved Successfully!!");
                    
                }
                else
                    errorText.setText("Enter the Pincode!!");
            }
            else
                errorText.setText("Address cannot be empty!!!!");
            
            });
            
        }catch (FilloException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    
    }    
    
