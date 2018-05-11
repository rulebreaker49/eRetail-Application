package myprojectjava;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static myprojectjava.loggedInUser.activeUserName;

public class CartController implements CommunicateData,loggedInUser{
    @FXML MenuButton menuButton;
    @FXML ScrollPane scrollPane;
    @FXML Text totalPrice;
    @FXML Text savingsText;
    @FXML Text payPrice;
    @FXML Text deliveryPrice;
    @FXML Button contShop;
    @FXML Button placeOrder;
      
    public void initialize(){
           
        MenuItem item1=new MenuItem("View Profile");
                MenuItem item2=new MenuItem("Edit Address");
                MenuItem item3=new MenuItem("Logout");
                menuButton.getItems().addAll(item1,item2,item3);
                item1.setOnAction(e->{
                try {
                Stage viewProf=new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/view_profile.fxml"));
                Scene scene = new Scene(root);
                viewProf.setScene(scene);
                viewProf.setResizable(false);
                viewProf.setTitle("Profile!!");
                viewProf.show();
        } catch (IOException ex) {
            Logger.getLogger(DashController.class.getName()).log(Level.SEVERE, null, ex);
        }
                });
                item2.setOnAction(e->{
                try {
                Stage addr=new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/my_address.fxml"));
                Scene scene = new Scene(root);
                addr.setScene(scene);
                addr.setResizable(false);
                addr.setTitle("Update Address!!");
                addr.show();
        } catch (IOException ex) {
            Logger.getLogger(DashController.class.getName()).log(Level.SEVERE, null, ex);
        }
                });
                item3.setOnAction(e->{
        mprod_img.clear();
        mprod_name.clear();
        mprod_price.clear();
        mprod_mrp.clear();
        mprod_quantity.clear();
        activeUserName.clear();
        System.exit(1);
                
                        });   
        if(mprod_name.isEmpty())
       {
        placeOrder.setDisable(true);
            ImageView empty=new ImageView("/image/empty-cart.gif");
            empty.setFitWidth(675);
            empty.setFitHeight(545);
            scrollPane.setContent(empty);
            return;
        }
        ArrayList<String> prod_img=new ArrayList<String>();
        ArrayList<String> prod_name=new ArrayList<String>();
        ArrayList<String> prod_price=new ArrayList<String>();
        ArrayList<String> prod_mrp=new ArrayList<String>();
        ArrayList<Integer> prod_quantity=new ArrayList<Integer>();
        prod_img=mprod_img;
        prod_name=mprod_name;
        prod_price=mprod_price;
        prod_mrp=mprod_mrp;
        prod_quantity=mprod_quantity;
           
        GridPane mainGrid=new GridPane();
        mainGrid.setVgap(2);
        long tot_price=0;
        long tot_mrp=0;
        for(int i=0;i<prod_name.size();i++)
        {
            AnchorPane pane=new AnchorPane();
            pane.setPrefWidth(680);
            pane.setPrefHeight(160);
            pane.setStyle("-fx-background-color:#ffffff;-fx-border-radius:3px");
            
            ImageView imag=new ImageView(prod_img.get(i));
            imag.setStyle("-fx-border-color:#000000;-fx-border-width:2px;-fx-border-radius:3px;");
            imag.setFitHeight(110);
            imag.setFitWidth(110);
            imag.setPreserveRatio(true);
            
            AnchorPane.setTopAnchor(imag,20.0);
            AnchorPane.setLeftAnchor(imag,20.0);
            AnchorPane.setBottomAnchor(imag,20.0);
            
            GridPane grid=new GridPane();
            grid.setVgap(5);
            grid.setHgap(5);
            AnchorPane.setTopAnchor(grid,15.0);
            AnchorPane.setLeftAnchor(grid,150.0);
            AnchorPane.setBottomAnchor(grid,15.0);
            AnchorPane.setRightAnchor(grid,15.0);
            
            Text txt=new Text(prod_name.get(i));
            txt.wrappingWidthProperty().set(500);
            txt.setFill(Color.DARKSLATEGRAY);
            txt.setStyle("-fx-font-size:12px;-fx-font-weight:bold;");
            grid.add(txt,0,0);
            
            HBox box=new HBox(10);
            Text prc=new Text("Rs "+prod_price.get(i));
            prc.setFill(Color.BLACK);
            prc.setFont(Font.font("Arial",FontWeight.BOLD,14));
            box.getChildren().add(prc);
            Text ret=new Text(prod_mrp.get(i));
            ret.setFill(Color.GRAY);
            ret.setFont(Font.font("Arial",12));
            ret.setStyle("-fx-strikethrough:true;");
            box.getChildren().add(ret);
            String price="";
            String mrp="";
            for(int ind=0;ind<prod_price.get(i).length();ind++)
            {
                char c=prod_price.get(i).charAt(ind);
                if(c!=',')
                    price+=c;
                
            }
            for(int ind=0;ind<prod_mrp.get(i).length();ind++)
            {
                char m=prod_mrp.get(i).charAt(ind);
                if(m!=',')
                    mrp+=m;
            }
            long int_price=Long.valueOf(price);
            long int_mrp=Long.valueOf(mrp);
            double dis=1-(double)int_price/int_mrp;
            int discount=(int)(dis*100.0);
            tot_price+=int_price*prod_quantity.get(i);
            tot_mrp+=int_mrp*prod_quantity.get(i);
            
            Text per=new Text(discount+"% off");
            per.setFill(Color.LIMEGREEN);
            per.setFont(Font.font("Arial",13));
            box.getChildren().add(per);
            box.setAlignment(Pos.CENTER_LEFT);
            grid.add(box,0,2);
            
            Label label=new Label("Quantity:");
            label.setStyle("-fx-font-size:14px");
            TextField fld=new TextField(Integer.toString(prod_quantity.get(i)));
            fld.setPrefWidth(30);
            HBox quantBox=new HBox(10);
            quantBox.setAlignment(Pos.BOTTOM_LEFT);
            quantBox.getChildren().addAll(label,fld);
            grid.add(quantBox,0,3);
            
            HBox btns=new HBox(10);
            btns.setAlignment(Pos.BOTTOM_RIGHT);
            btns.getStylesheets().add(MyProjectJava.class.getResource("finalCartButtons.css").toExternalForm());        
            Button updateCart=new Button("Update");
            updateCart.setPrefWidth(100);
            String nmm=Integer.toString(prod_quantity.get(i));
            updateCart.setOnAction(e->{
            if(!fld.getText().equals(""))
                updateQuantity(Integer.parseInt(fld.getText()),txt.getText());        
            else
                fld.setText(nmm);
            });
            Button removeCart=new Button("Remove");
            removeCart.setOnAction(e->removeQuantity(txt.getText()));
            removeCart.setPrefWidth(100);
            removeCart.setStyle("-fx-background-color:#ffffff");
            btns.getChildren().addAll(updateCart,removeCart);
            AnchorPane.setRightAnchor(btns,25.0);
            AnchorPane.setBottomAnchor(btns,25.0);
            
            pane.getChildren().addAll(imag,grid,btns);
            mainGrid.add(pane,0,i); 
        }
        scrollPane.setContent(mainGrid);
        
        int del=0;
        if(tot_price<500)
            del=50;
        deliveryPrice.setText("Rs "+Integer.toString(del));
        totalPrice.setText("Rs "+Long.toString(tot_price));
        payPrice.setText("Rs "+Long.toString(tot_price+del));
        savingsText.setText("Your total savings are Rs "+Long.toString(tot_mrp-tot_price));
        
    }
    public void updateView(){
    ArrayList<String> prod_img=new ArrayList<String>();
        ArrayList<String> prod_name=new ArrayList<String>();
        ArrayList<String> prod_price=new ArrayList<String>();
        ArrayList<String> prod_mrp=new ArrayList<String>();
        ArrayList<Integer> prod_quantity=new ArrayList<Integer>();
        prod_img=mprod_img;
        prod_name=mprod_name;
        prod_price=mprod_price;
        prod_mrp=mprod_mrp;
        prod_quantity=mprod_quantity;
           
        GridPane mainGrid=new GridPane();
        mainGrid.setVgap(2);
        long tot_price=0;
        long tot_mrp=0;
        for(int i=0;i<prod_name.size();i++)
        {
            AnchorPane pane=new AnchorPane();
            pane.setPrefWidth(680);
            pane.setPrefHeight(160);
            pane.setStyle("-fx-background-color:#ffffff;-fx-border-radius:3px");
            
            ImageView imag=new ImageView(prod_img.get(i));
            imag.setStyle("-fx-border-color:#000000;-fx-border-width:2px;-fx-border-radius:3px;");
            imag.setFitHeight(110);
            imag.setFitWidth(110);
            imag.setPreserveRatio(true);
            
            AnchorPane.setTopAnchor(imag,20.0);
            AnchorPane.setLeftAnchor(imag,20.0);
            AnchorPane.setBottomAnchor(imag,20.0);
            
            GridPane grid=new GridPane();
            grid.setVgap(5);
            grid.setHgap(5);
            AnchorPane.setTopAnchor(grid,15.0);
            AnchorPane.setLeftAnchor(grid,150.0);
            AnchorPane.setBottomAnchor(grid,15.0);
            AnchorPane.setRightAnchor(grid,15.0);
            
            Text txt=new Text(prod_name.get(i));
            txt.wrappingWidthProperty().set(500);
            txt.setFill(Color.DARKSLATEGRAY);
            txt.setStyle("-fx-font-size:12px;-fx-font-weight:bold;");
            grid.add(txt,0,0);
            
            HBox box=new HBox(10);
            Text prc=new Text("Rs "+prod_price.get(i));
            prc.setFill(Color.BLACK);
            prc.setFont(Font.font("Arial",FontWeight.BOLD,14));
            box.getChildren().add(prc);
            Text ret=new Text(prod_mrp.get(i));
            ret.setFill(Color.GRAY);
            ret.setFont(Font.font("Arial",12));
            ret.setStyle("-fx-strikethrough:true;");
            box.getChildren().add(ret);
            String price="";
            String mrp="";
            for(int ind=0;ind<prod_price.get(i).length();ind++)
            {
                char c=prod_price.get(i).charAt(ind);
                if(c!=',')
                    price+=c;
                
            }
            for(int ind=0;ind<prod_mrp.get(i).length();ind++)
            {
                char m=prod_mrp.get(i).charAt(ind);
                if(m!=',')
                    mrp+=m;
            }
            long int_price=Long.valueOf(price);
            long int_mrp=Long.valueOf(mrp);
            double dis=1-(double)int_price/int_mrp;
            int discount=(int)(dis*100.0);
            tot_price+=int_price*prod_quantity.get(i);
            tot_mrp+=int_mrp*prod_quantity.get(i);
            
            Text per=new Text(discount+"% off");
            per.setFill(Color.LIMEGREEN);
            per.setFont(Font.font("Arial",13));
            box.getChildren().add(per);
            box.setAlignment(Pos.CENTER_LEFT);
            grid.add(box,0,2);
            
            Label label=new Label("Quantity:");
            label.setStyle("-fx-font-size:14px");
            TextField fld=new TextField(Integer.toString(prod_quantity.get(i)));
            fld.setPrefWidth(30);
            HBox quantBox=new HBox(10);
            quantBox.setAlignment(Pos.BOTTOM_LEFT);
            quantBox.getChildren().addAll(label,fld);
            grid.add(quantBox,0,3);
            
            HBox btns=new HBox(10);
            btns.setAlignment(Pos.BOTTOM_RIGHT);
            btns.getStylesheets().add(MyProjectJava.class.getResource("finalCartButtons.css").toExternalForm());        
            Button updateCart=new Button("Update");
            updateCart.setPrefWidth(100);
            String nmm=Integer.toString(prod_quantity.get(i));
            updateCart.setOnAction(e->{
            if(!fld.getText().equals(""))
                updateQuantity(Integer.parseInt(fld.getText()),txt.getText());        
            else
                fld.setText(nmm);
            });
            Button removeCart=new Button("Remove");
            removeCart.setOnAction(e->removeQuantity(txt.getText()));
            removeCart.setPrefWidth(100);
            removeCart.setStyle("-fx-background-color:#ffffff");
            btns.getChildren().addAll(updateCart,removeCart);
            AnchorPane.setRightAnchor(btns,25.0);
            AnchorPane.setBottomAnchor(btns,25.0);
            
            pane.getChildren().addAll(imag,grid,btns);
            mainGrid.add(pane,0,i); 
        }
        scrollPane.setContent(mainGrid);
        
        int del=0;
        if(tot_price<500)
            del=50;
        deliveryPrice.setText("Rs "+Integer.toString(del));
        totalPrice.setText("Rs "+Long.toString(tot_price));
        payPrice.setText("Rs "+Long.toString(tot_price+del));
        savingsText.setText("Your total savings are Rs "+Long.toString(tot_mrp-tot_price));
    }

    private void updateQuantity(int parseInt, String get) {
       int i=0;
        while(i<mprod_name.size())
        {
            if(mprod_name.get(i).equals(get))
                break;
            i++;
        }
        mprod_quantity.set(i,parseInt);
        updateView();
    }
    private void removeQuantity(String get) {
        int i=0;
        while(i<mprod_name.size())
        {
            if(mprod_name.get(i).equals(get))
                break;
            i++;
        }
        mprod_quantity.remove(i);
        mprod_img.remove(i);
        mprod_name.remove(i);
        mprod_price.remove(i);
        mprod_mrp.remove(i);
        updateView();
    }
    public void returnToDash()
    {
       Stage dashStage=(Stage) scrollPane.getScene().getWindow();
            try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/dashboard_layout.fxml"));
            Scene scene = new Scene(root);
            dashStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        dashStage.setTitle("Dashboard!!");
        dashStage.setResizable(false);
        dashStage.show();
            
    }
    public void placeOrderCall()
    {
        String srch=activeUserName.get(0);
        Fillo fillo=new Fillo();
        try {
            Connection connection2 = fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/userData.xlsx");
            Recordset recordset = connection2.executeQuery("Select * from Sheet1 where Username='"+srch+"'");
            recordset.next();
            String address=recordset.getField("Address");
            String pincode=recordset.getField("Pincode");
            if(address.equals("null")||pincode.equals("null"))
            {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Address!!");
                        alert.setHeaderText(null);
                        alert.setContentText("Please Update Address in Your Account!!");
                        alert.showAndWait(); 
            return;
            }
        }catch (FilloException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
       placeOrder.setStyle("-fx-background-color:#e28000;-fx-border-color:#c9c9c9;");
       mprod_img.clear();
       mprod_name.clear();
       mprod_price.clear();
       mprod_mrp.clear();
       mprod_quantity.clear();
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Order Success!!");
                        alert.setHeaderText(null);
                        alert.setContentText("Your Order will be delivered in 5-7 days");
                        alert.showAndWait(); 
       returnToDash();
    }
}
