package MultiThreading.learning;

public class MTSynchronizedBlock {

	public static void main(String[] args) {
		SharedResourcePrinterBlock sharedResourcePrinter = new SharedResourcePrinterBlock();

		PrinterThreadBlock printerThread1 = new PrinterThreadBlock("ooo", sharedResourcePrinter);
		PrinterThreadBlock printerThread2 = new PrinterThreadBlock("ddd", sharedResourcePrinter);
		PrinterThreadBlock printerThread3 = new PrinterThreadBlock("ttttttttttt", sharedResourcePrinter);

		try {
			printerThread1.thread.join();
			printerThread2.thread.join();
			printerThread3.thread.join();
		} catch (InterruptedException ex) {
			System.out.println("Main interrupted.");
		}

	}
}

class PrinterThreadBlock implements Runnable {

	String message;
	Thread thread;
	SharedResourcePrinterBlock sharedResourcePrinter;

	public PrinterThreadBlock(String message, SharedResourcePrinterBlock sharedResourcePrinter) {
		this.message = message;
		this.thread = new Thread(this);
		this.sharedResourcePrinter = sharedResourcePrinter;

		this.thread.start();
	}

	public void run() {
		synchronized(this.sharedResourcePrinter) {
			this.sharedResourcePrinter.secondPrint(message);
		}
		synchronized(this.sharedResourcePrinter) {
			this.sharedResourcePrinter.print(message);
		}
	}
}

class SharedResourcePrinterBlock {
	boolean skipSleep = false;
	double time = 0.1;

	void secondPrint(String message) {
		MTUtility.sleep(time, skipSleep);
		for (int i = 0; i < message.length(); i++) {
			System.out.print(message.charAt(i));
			MTUtility.sleep(time, skipSleep);
		}
		System.out.println();
		MTUtility.sleep(time, skipSleep);
	}
	
	void print(String message) {
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
