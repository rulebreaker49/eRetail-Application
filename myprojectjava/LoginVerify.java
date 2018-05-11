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

class LoginVerify {
    public int verifyUser(String username, String password)
    {
        Fillo fillo=new Fillo();
        Connection connection = null;
        try {
            connection = fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/userData.xlsx");
            Recordset recordset = connection.executeQuery("Select * from Sheet1 where Username='"+username+"' and Password='"+password+"'");
        } catch (FilloException ex) {
            if(connection!=null)
                connection.close();
            return 0;
        }
        if(connection!=null)
            connection.close();
        return 1;
    }
}
