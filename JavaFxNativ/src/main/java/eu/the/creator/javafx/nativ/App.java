package eu.the.creator.javafx.nativ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Hello world!
 *
 */
public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(StartController.class.getResource("Start.fxml"));

		// Die Controllerfactory ist interessant wenn man nicht den Loader
		// den Controller per Reflection instanzieeren möchte.

		// Wenn dieser nicht gesetzt wird kommt es dazu:
		// java.lang.NoSuchMethodException:
		// eu.the.creator.javafx.nativ.StartController.<init>()
		loader.setControllerFactory(new Callback<Class<?>, Object>() {

			@Override
			public Object call(Class<?> param) {
				return new StartController("Ohne gehts nicht");
			}
		});
		loader.load();

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);

		primaryStage.setOnCloseRequest((evt) -> {
			// Hauptstage wird geschlossen. Jetzt Verbindungen zu anderen Hosts
			// schließen
				System.exit(0);
			});
		// Den Eventdispatcher 1* Wrappen damit die Exception nicht verschluckt
		// werden.
		primaryStage.setEventDispatcher(new ExceptionHandler(primaryStage.getEventDispatcher(), null));

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Einen einfachen Exceptionhandler
}
