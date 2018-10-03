package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class answerController  {
	@FXML
	private TextField oneUserAns;
	void setAns(String input) {
		   oneUserAns.setText(input);
	   }
	
}
