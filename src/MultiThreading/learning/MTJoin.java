package MultiThreading.learning;

public class MTJoin {
	public static void main(String[] args) {
		NewThread thread1 = new NewThread("One");
		NewThread thread2 = new NewThread("Two");
		NewThread thread3 = new NewThread("Three");
		
		try{
//			Thread.sleep(5000);
			thread1.thread.join();
			thread2.thread.join();
			thread3.thread.join();
		} catch(Exception e) {
			System.out.println("Main interrepted.");
		}
		System.out.println("\nMain exiting.\n");
	}
}

class NewThread implements Runnable {
	
	String name;
	Thread thread;
	
	NewThread(String name) {
		this.name = name;
		this.thread = new Thread(this, this.name);
		
		System.out.println("New Thread: " + this.thread);
		this.thread.start();
	}
	
	public void run() {
		try {
			for(int i=0; i<5; i++) {
				System.out.println(this.name + ":" + i);
				Thread.sleep(1000);
			}
		} catch(Exception e) {
			System.out.println(this.name + " interrepted");
		}
		
		System.out.println(this.name + " exiting.");
	}
}