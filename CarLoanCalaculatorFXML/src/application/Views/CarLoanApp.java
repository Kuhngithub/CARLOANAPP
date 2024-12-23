package application.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarLoanApp extends Application 
{

    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
        	Parent root = FXMLLoader.load(getClass().getResource("/application/Views/CarLoanCalculator.fxml"));
            Scene scene = new Scene(root, 800, 600);
            
            primaryStage.setTitle("Car Loan Calculator");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}

