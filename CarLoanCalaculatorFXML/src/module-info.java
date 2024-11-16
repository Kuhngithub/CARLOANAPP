/**
 * 
 */
/**
 * 
 */
module CarLoanCalaculatorFXML {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	exports application.Models;
	exports application.Controllers to javafx.fxml;
	exports application.Views;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.Controllers to javafx.fxml;
	opens application.Views to javafx.fxml;
}
