package eu.the.creator.javafx.nativ;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
/**
 * Ein einfacher Exceptionhandler der einen Dialog öffnet wenn es eine Exception gibt
 * @author scavenger156
 *
 */
public class ExceptionHandler implements EventDispatcher {

	private EventDispatcher org;
	private UncaughtExceptionHandler handler;
/**
 * 
 * @param org Eventdispatcher der gewrappt wird
 * @param handler alternativer Exceptionhandler. Ist dieser null zeigen wir einen Exceptiondialog an.
 */
	public ExceptionHandler(EventDispatcher org, UncaughtExceptionHandler handler) {
		this.org = org;
		this.handler = handler;
	}

	@Override
	public Event dispatchEvent(Event event, EventDispatchChain edc) {
		try {
			return org.dispatchEvent(event, edc);
		} catch (ConsumedException ex) {
			throw ex;
		} catch (RuntimeException e) {
			if (e instanceof NoSuchElementException) {
				throw new ConsumedException(e);
			}
			if (e.getCause() instanceof InvocationTargetException) {
				InvocationTargetException ite = (InvocationTargetException) e.getCause();
				final Throwable targetException = ite.getTargetException();

				if (handler != null) {
					handler.uncaughtException(null, targetException);
				} else {
					showException(targetException);
				}
			} else {

				if (handler != null) {
					handler.uncaughtException(null, e);
				} else {
					showException(e);
				}
			}
			throw new ConsumedException(e);
		} catch (Exception e) {

			if (handler != null) {
				handler.uncaughtException(null, e);
			} else {
				showException(e);
			}
			throw new ConsumedException(e);
		}
	}

	private void showException(Throwable ex) {
		if (!Platform.isFxApplicationThread()) {
			Platform.runLater(() -> {
				showException(ex);
			});
			return;
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fehler gefunden");
		alert.setHeaderText("Fehler in der Anwendung gefunden");
		alert.setContentText(ex.getLocalizedMessage());
		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);

		alert.getDialogPane().setExpandableContent(textArea);
		alert.showAndWait();
	}

	/**
	 * Doppelte Exceptions vermeiden
	 */
	private static class ConsumedException extends RuntimeException {

		public ConsumedException(Throwable cause) {
			super(cause);
		}
	}
}
