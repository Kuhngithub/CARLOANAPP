package application.Controllers;

import application.Models.CarLoansModels;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class CarLoanController {
    @FXML private RadioButton carRadioButton;
    @FXML private RadioButton truckRadioButton;
    @FXML private RadioButton vanRadioButton;
    @FXML private RadioButton newRadioButton;
    @FXML private RadioButton usedRadioButton;
    @FXML private RadioButton weeklyRadioButton;
    @FXML private RadioButton biWeeklyRadioButton;
    @FXML private RadioButton monthlyRadioButton;
    @FXML private TextField priceTextField;
    @FXML private TextField downPaymentTextField;
    @FXML private TextField interestRateTextField;
    @FXML private Slider loanPeriodSlider;
    @FXML private TextField estimatedPaymentTextField;
    @FXML private Button savedRatesButton;

    private CarLoansModels loanModel = new CarLoansModels();
    private ObservableList<Double> savedPayments = FXCollections.observableArrayList();


    @FXML
    public void initialize() 
    {
        
    	ToggleGroup vehicleTypeGroup = new ToggleGroup();
        ToggleGroup vehicleAgeGroup = new ToggleGroup();
        ToggleGroup paymentFrequencyGroup = new ToggleGroup();

        carRadioButton.setToggleGroup(vehicleTypeGroup);
        truckRadioButton.setToggleGroup(vehicleTypeGroup);
        vanRadioButton.setToggleGroup(vehicleTypeGroup);

        newRadioButton.setToggleGroup(vehicleAgeGroup);
        usedRadioButton.setToggleGroup(vehicleAgeGroup);

        weeklyRadioButton.setToggleGroup(paymentFrequencyGroup);
        biWeeklyRadioButton.setToggleGroup(paymentFrequencyGroup);
        monthlyRadioButton.setToggleGroup(paymentFrequencyGroup);

    }

    
    @FXML
    private void showSavedRatesDialog() 
    {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(savedRatesButton.getScene().getWindow());
        dialogStage.setTitle("Saved Payments");

        ListView<Double> listView = new ListView<>();
        listView.setItems(savedPayments);
        listView.setCellFactory(param -> new ListCell<Double>() 
        {
            @Override
            protected void updateItem(Double item, boolean empty) 
            {
                super.updateItem(item, empty);
                if (empty || item == null) 
                {
                    setText(null);
                } 
                else 
                {
                    setText("$" + String.format("%.2f", item));
                }
            }
        });

        VBox vBox = new VBox(listView);
        Scene scene = new Scene(vBox, 300, 400);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    

    @FXML
    public void calculatePayment() 
    {
        try 
        {
            double price = Double.parseDouble(priceTextField.getText().replace("$", ""));
            double downPayment = Double.parseDouble(downPaymentTextField.getText().replace("$", ""));
            double interestRate = Double.parseDouble(interestRateTextField.getText().replace("%", "")) / 100.0;
            int months = (int) loanPeriodSlider.getValue();

            CarLoansModels.PaymentFrequency freq = weeklyRadioButton.isSelected() ? CarLoansModels.PaymentFrequency.WEEKLY :
                    (biWeeklyRadioButton.isSelected() ? CarLoansModels.PaymentFrequency.BI_WEEKLY : CarLoansModels.PaymentFrequency.MONTHLY);
            
            System.out.println(String.format("Calculating payment with: price=%f, downPayment=%f, interestRate=%f, months=%d", price, downPayment, interestRate, months));
            double payment = loanModel.computePayment(price, downPayment, interestRate, months, freq);
            estimatedPaymentTextField.setText("$" + String.format("%.2f", payment));

            if (!savedPayments.contains(payment)) 
            {
                savedPayments.add(payment);
            }

        } 
        catch (Exception ex) 
        {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Input Error");
            errorAlert.setContentText("Please ensure all inputs are correctly filled and try again.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void clearFields() 
    {
        carRadioButton.setSelected(false);
        truckRadioButton.setSelected(false);
        vanRadioButton.setSelected(false);
        newRadioButton.setSelected(false);
        usedRadioButton.setSelected(false);
        weeklyRadioButton.setSelected(false);
        biWeeklyRadioButton.setSelected(false);
        monthlyRadioButton.setSelected(false);
        
        priceTextField.clear();
        downPaymentTextField.clear();
        interestRateTextField.clear();
        loanPeriodSlider.setValue(loanPeriodSlider.getMin());
        estimatedPaymentTextField.clear();
        //savedPayments.clear();
    }
}
