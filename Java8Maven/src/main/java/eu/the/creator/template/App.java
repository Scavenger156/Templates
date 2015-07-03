package eu.the.creator.template;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
    	launch(App.class, args);
       
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		System.exit(0);
	}
}
