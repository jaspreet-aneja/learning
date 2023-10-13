package MultiThreading.learning;

import java.util.concurrent.Exchanger;

/**
 * It is designed to simplify the exchange of data between two threads. It waits
 * until two separate threads call its exchange() method. When that occurs, it
 * simply exchanges the data supplied by the threads. For example, one thread
 * might prepare a buffer for receiving information over a network connection.
 * Another thread might fill that buffer with the information from the
 * connection. The two threads work together so that each time a new buffer is
 * needed, as exchange is made.
 * 
 * @author Jaspreet
 *
 */
public class MTExchanger {
	public static void main(String[] args) {
		Exchanger<String> ex = new Exchanger<>();

		new UseString(ex);
		new MakeString(ex);
	}
}

class MakeString implements Runnable {
	Exchanger<String> ex;
	String str = "";

	public MakeString(Exchanger<String> ex) {
		this.ex = ex;
		new Thread(this).start();
	}

	public void run() {
		char ch = 'A';
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				str += ch++;
			}

			try {
				str = ex.exchange(str);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class UseString implements Runnable {
	Exchanger<String> ex;
	String str;

	public UseString(Exchanger<String> ex) {
		this.ex = ex;
		new Thread(this).start();
	}

	public void run() {

		for (int i = 0; i < 3; i++) {
			try {
				str = ex.exchange(new String());
				System.out.println("Got " + str);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}