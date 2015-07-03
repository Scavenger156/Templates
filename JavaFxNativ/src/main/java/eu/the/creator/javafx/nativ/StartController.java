package eu.the.creator.javafx.nativ;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class StartController implements Initializable {
	@FXML
	private Button btnExcepetion;

	/**
	 * Durch diesen Konstruktor lässt sich der FXMLLoader nicht mehr so wie
	 * gewohnt verwenden.
	 * 
	 * @param dummyParameter
	 */
	public StartController(String dummyParameter) {

	}

	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void actionMakeException() {
		Integer zero = null;
		zero.toString();
	}
}
