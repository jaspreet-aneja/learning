package MultiThreading.learning;

public class MTSynchronizedMethod {

	public static void main(String[] args) {
		SharedResourcePrinter sharedResourcePrinter = new SharedResourcePrinter();

		PrinterThread printerThread1 = new PrinterThread("ooooooooooo", sharedResourcePrinter);
		PrinterThread printerThread2 = new PrinterThread("ddddddddddd", sharedResourcePrinter);
		PrinterThread printerThread3 = new PrinterThread("ttttttttttt", sharedResourcePrinter);

		try {
			printerThread1.thread.join();
			printerThread2.thread.join();
			printerThread3.thread.join();
		} catch (InterruptedException ex) {
			System.out.println("Main interrupted.");
		}

	}
}

class PrinterThread implements Runnable {

	String message;
	Thread thread;
	SharedResourcePrinter sharedResourcePrinter;

	public PrinterThread(String message, SharedResourcePrinter sharedResourcePrinter) {
		this.message = message;
		this.thread = new Thread(this);
		this.sharedResourcePrinter = sharedResourcePrinter;

		this.thread.start();
	}

	public void run() {
		this.sharedResourcePrinter.secondPrint(message);
		this.sharedResourcePrinter.print(message);
	}
}

class SharedResourcePrinter {
	boolean skipSleep = false;
	double time = 0.5;

	synchronized void secondPrint(String message) {
		MTUtility.sleep(time, skipSleep);
		for (int i = 0; i < message.length(); i++) {
			System.out.print(message.charAt(i));
			MTUtility.sleep(time, skipSleep);
		}
		System.out.println();
		MTUtility.sleep(time, skipSleep);
	}
	
	synchronized void print(String message) {
		System.out.print("[");

		MTUtility.sleep(time, skipSleep);

		for (int i = 0; i < message.length(); i++) {
			System.out.print(message.charAt(i));
			MTUtility.sleep(time, skipSleep);
		}

		MTUtility.sleep(time, skipSleep);

		System.out.println("]");
	}
}