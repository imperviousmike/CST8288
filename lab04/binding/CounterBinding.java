package binding;

import java.util.concurrent.TimeUnit;

import counter.Counter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public class CounterBinding extends Task<Void> {

	private Counter counter;
	private StringProperty time;
	private Object stepLock;

	public CounterBinding(Counter counter) {
		this.counter = counter;
		stepLock = new Object();
		time = new SimpleStringProperty();
		updateMessage(counter.toString());
		time.bind(messageProperty());
	}

	protected Void call() {
		while (!isCancelled()) {
			try {
				if (counter.isPaused()) {
					synchronized (stepLock) {
						stepLock.wait();
					}
				}

				if (!counter.step()) {
					TimeUnit.MILLISECONDS.sleep(50);
				} else {
					updateMessage(counter.toString());
				}

			} catch (InterruptedException ex) {

			}
		}
		return null;

	}

	public StringProperty timeProperty() {
		return time;
	}

	public void start() {
		counter.start();
		synchronized (stepLock) {
			stepLock.notifyAll();
		}

	}

	public void stop() {
		counter.stop();
	}

	public void pause() {
		counter.pause();
	}

	public void shutdown() {
		this.cancel();
	}

}
