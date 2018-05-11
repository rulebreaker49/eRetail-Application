/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectjava;

import java.lang.String;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static myprojectjava.CommunicateData.mprod_img;
import static myprojectjava.CommunicateData.mprod_mrp;
import static myprojectjava.CommunicateData.mprod_name;
import static myprojectjava.CommunicateData.mprod_price;
import static myprojectjava.CommunicateData.mprod_quantity;
import static myprojectjava.loggedInUser.activeUserName;

/**
 *
 * @author Gaurav Jindal
 */
public class DashController implements CommunicateData{    
    @FXML MenuButton menuButton;
    @FXML ListView listView;
    @FXML ScrollPane scrollPane;
    @FXML Label itemCount;
    @FXML Button cartButton;
    
    int items=0;
    
    String query="Select * from Sheet1";
    public void itemClicked()
        {
        String fltrr=(String) listView.getSelectionModel().getSelectedItem();
        if(fltrr.contains("All Categories"))
            query="Select * from Sheet1";
        if(fltrr.contains("Electronics"))
            query="Select * from Sheet1 where Category='one'";
        if(fltrr.contains("Fashion & Apparel"))
            query="Select * from Sheet1 where Category='two'";
        if(fltrr.contains("Home & Furniture"))
            query="Select * from Sheet1 where Category='three'";
        if(fltrr.contains("Kitchen & Appliances"))
            query="Select * from Sheet1 where Category='four'";
        if(fltrr.contains("Books & More"))
            query="Select * from Sheet1 where Category='five'";
        if(fltrr.contains("Mobiles and Computers"))
            query="Select * from Sheet1 where Category='six'";
        if(fltrr.contains("Beuty & Health"))
            query="Select * from Sheet1 where Category='seven'";
        if(fltrr.contains("Groceries"))
            query="Select * from Sheet1 where Category='eight'";
        if(fltrr.contains("Toys & Baby Products"))
            query="Select * from Sheet1 where Category='nine'";
        updateContent();
        }
    public void initialize()
    {
        for(int i=0;i<mprod_quantity.size();i++)
            items+=mprod_quantity.get(i);
        itemCount.setText(Integer.toString(items));
                  ImageView imgV=new ImageView("/image/cart.png");
                  imgV.setFitHeight(26);
                  imgV.setFitWidth(25);
                  imgV.setTranslateY(1.0);
                  cartButton.setGraphic(imgV);

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
                listView.getItems().addAll("All Categories","Electronics","Fashion & Apparel","Home & Furniture","Kitchen & Appliances","Books & More","Mobiles and Computers","Beuty & Health","Groceries","Toys & Baby Products");
                  GridPane mainGrid=new GridPane();
                  mainGrid.setPadding(new Insets(10,10,10,10));
                  mainGrid.setVgap(5);
                  mainGrid.setHgap(5);        
                  Fillo fillo=new Fillo();
                  int i=0,j=0;
                  Connection connection = null;
                  try {
                      connection = fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/productCatalogue.xlsx");
                      Recordset recordset = connection.executeQuery(query);
                      while(recordset.next())
                      {
                          AnchorPane pane=new AnchorPane();
                          pane.setStyle("-fx-background-color:#ffffff;-fx-border-radius:3px");

                          String img="/image/"+recordset.getField("Product Image");
                          String prod=recordset.getField("Product Name");
                          String price=recordset.getField("Product Price");
                          String mrp=recordset.getField("MRP");
                          String rating=recordset.getField("Rating");
                          String sold=recordset.getField("Sold");
                          String discount=recordset.getField("Discount");


                          ImageView imag=new ImageView(img);
                          imag.setStyle("-fx-border-color:#000000;-fx-border-width:2px;-fx-border-radius:3px;");
                          imag.setFitHeight(170);
                          imag.setFitWidth(185);
                          imag.setPreserveRatio(true);

                          AnchorPane.setTopAnchor(imag,10.0);
                          AnchorPane.setLeftAnchor(imag,10.0);
                          AnchorPane.setRightAnchor(imag,10.0);

                          GridPane grid=new GridPane();
                          grid.setVgap(5);
                          grid.setHgap(5);        

                          Text txt=new Text(prod);
                          txt.wrappingWidthProperty().set(185);
                          txt.setFill(Color.DARKSLATEGRAY);
                          txt.setStyle("-fx-font-size:12px;-fx-font-weight:bold;");
                          grid.add(txt,0,0);

                          HBox box=new HBox(10);
                          Text prc=new Text("Rs "+price);
                          prc.setFill(Color.BLACK);
                          prc.setFont(Font.font("Arial",FontWeight.BOLD,14));
                          box.getChildren().add(prc);
                          Text ret=new Text(mrp);
                          ret.setFill(Color.GRAY);
                          ret.setFont(Font.font("Arial",12));
                          ret.setStyle("-fx-strikethrough:true;");
                          box.getChildren().add(ret);
                          Text per=new Text(discount+"% off");
                          per.setFill(Color.LIMEGREEN);
                          per.setFont(Font.font("Arial",13));
                          box.getChildren().add(per);
                          box.setAlignment(Pos.CENTER_LEFT);
                          grid.add(box,0,2);

                          HBox rat=new HBox(10);
                          Label lab=new Label(rating);
                          lab.setStyle("-fx-text-fill:#ffffff;-fx-background-color:#42bf48;-fx-label-padding:2 5 2 5;-fx-background-radius:3px;");
                          ImageView star=new ImageView("/image/rating.png");
                          star.setFitWidth(12);
                          star.setFitHeight(13);
                          lab.setGraphic(star);
                          rat.getChildren().add(lab);
                          Text rater=new Text("("+sold+")");
                          rater.setFill(Color.GRAY);
                          rater.setFont(Font.font("Arial",12));
                          rat.getChildren().add(rater);
                          rat.setAlignment(Pos.CENTER_LEFT);
                          grid.add(rat,0,3);
                          Button btn=new Button("Add to Cart");
                          btn.getStylesheets().add(MyProjectJava.class.getResource("listViewStyle.css").toExternalForm());
                          btn.setOnAction(e->{
                              updateCart(img,prod,price,mrp);
                          });
                          AnchorPane.setTopAnchor(btn,280.0);
                          AnchorPane.setLeftAnchor(btn,10.0);
                          AnchorPane.setRightAnchor(btn,10.0);
                          AnchorPane.setBottomAnchor(btn,10.0);

                          AnchorPane.setTopAnchor(grid,190.0);
                          AnchorPane.setLeftAnchor(grid,10.0);
                          AnchorPane.setRightAnchor(grid,10.0);

                          pane.getChildren().addAll(imag,grid,btn);
                          mainGrid.add(pane,i,j);

                          if(i<3)
                              i++;
                          else
                          {
                              i=0;
                              j++;
                          }
                      }
                  } catch (FilloException ex) {
                      System.out.println("Connection failed");
                  }
                  scrollPane.setContent(mainGrid);
             
    }
    public void updateCart(String img,String prod,String price,String mrp)
    {
        items++;
        itemCount.setText(Integer.toString(items));
        int chk=0;
        for(int i=0;i<mprod_name.size();i++)
        {
        if(mprod_name.get(i).equals(prod))
        {
        chk=1;
        int tmp=mprod_quantity.get(i);
        mprod_quantity.set(i,tmp+1);
        }
        }
        if(chk==0)
        {
            mprod_img.add(img);
            mprod_name.add(prod);
            mprod_price.add(price);
            mprod_mrp.add(mrp);
            mprod_quantity.add(1);
        }
    }
    public void updateContent()
    {      
        GridPane mainGrid=new GridPane();
        mainGrid.setPadding(new Insets(10,10,10,10));
        mainGrid.setVgap(5);
        mainGrid.setHgap(5);        
        
    try {
            Fillo fillo=new Fillo();
            int i=0,j=0;
            Connection connection = null;
            connection = fillo.getConnection("E:/JavaHoliday/MyJavaProject/MyProjectJava/database/productCatalogue.xlsx");
            Recordset recordset = connection.executeQuery(query);
            while(recordset.next())
            {
                AnchorPane pane=new AnchorPane();
                pane.setStyle("-fx-background-color:#ffffff;-fx-border-radius:3px");
                
                String img="/image/"+recordset.getField("Product Image");
                String prod=recordset.getField("Product Name");
                String price=recordset.getField("Product Price");
                String mrp=recordset.getField("MRP");
                String rating=recordset.getField("Rating");
                String sold=recordset.getField("Sold");
                String discount=recordset.getField("Discount");
                
                ImageView imag=new ImageView(img);
                imag.setStyle("-fx-border-color:#000000;-fx-border-width:2px;-fx-border-radius:3px;");
                imag.setFitHeight(170);
                imag.setFitWidth(185);
                imag.setPreserveRatio(true);
                
                AnchorPane.setTopAnchor(imag,10.0);
                AnchorPane.setLeftAnchor(imag,10.0);
                AnchorPane.setRightAnchor(imag,10.0);
                
                GridPane grid=new GridPane();
                grid.setVgap(5);
                grid.setHgap(5);        
                
                Text txt=new Text(prod);
                txt.wrappingWidthProperty().set(185);
                txt.setFill(Color.DARKSLATEGRAY);
                txt.setStyle("-fx-font-size:12px;-fx-font-weight:bold;");
                grid.add(txt,0,0);
                
                HBox box=new HBox(10);
                Text prc=new Text("Rs "+price);
                prc.setFill(Color.BLACK);
                prc.setFont(Font.font("Arial",FontWeight.BOLD,14));
                box.getChildren().add(prc);
                Text ret=new Text(mrp);
                ret.setFill(Color.GRAY);
                ret.setFont(Font.font("Arial",12));
                ret.setStyle("-fx-strikethrough:true;");
                box.getChildren().add(ret);
                Text per=new Text(discount+"% off");
                per.setFill(Color.LIMEGREEN);
                per.setFont(Font.font("Arial",13));
                box.getChildren().add(per);
                box.setAlignment(Pos.CENTER_LEFT);
                grid.add(box,0,2);
                
                HBox rat=new HBox(10);
                Label lab=new Label(rating);
                lab.setStyle("-fx-text-fill:#ffffff;-fx-background-color:#42bf48;-fx-label-padding:2 5 2 5;-fx-background-radius:3px;");
                ImageView star=new ImageView("/image/rating.png");
                star.setFitWidth(12);
                star.setFitHeight(13);
                lab.setGraphic(star);
                rat.getChildren().add(lab);
                Text rater=new Text("("+sold+")");
                rater.setFill(Color.GRAY);
                rater.setFont(Font.font("Arial",12));
                rat.getChildren().add(rater);
                rat.setAlignment(Pos.CENTER_LEFT);
                grid.add(rat,0,3);
                Button btn=new Button("Add to Cart");
                btn.getStylesheets().add(MyProjectJava.class.getResource("listViewStyle.css").toExternalForm());
                btn.setOnAction(e->{
                    updateCart(img,prod,price,mrp);
                });
                AnchorPane.setTopAnchor(btn,280.0);
                AnchorPane.setLeftAnchor(btn,10.0);
                AnchorPane.setRightAnchor(btn,10.0);
                AnchorPane.setBottomAnchor(btn,10.0);
                
                AnchorPane.setTopAnchor(grid,190.0);
                AnchorPane.setLeftAnchor(grid,10.0);
                AnchorPane.setRightAnchor(grid,10.0);
                
                pane.getChildren().addAll(imag,grid,btn);
                mainGrid.add(pane,i,j);
                
                if(i<3)
                    i++;
                else
                {
                    i=0;
                    j++;
                }
            }
        } catch (FilloException ex) {
            System.out.println("Connection failed");
        }
        scrollPane.setContent(mainGrid);        
    }
    public void openCart()
    {
       try {
            Stage cart=(Stage) cartButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/shopcart_layout.fxml"));
            Scene scene = new Scene(root);
            cart.setScene(scene);
            cart.setTitle("Cart!!");
            cart.setResizable(false);
            cart.show();
        } catch (IOException ex) {
            Logger.getLogger(DashController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
