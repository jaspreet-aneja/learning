package MultiThreading.learning;

import java.util.concurrent.Semaphore;

public class MTSemaphore {
	public static void main(String[] args) {
		Semaphore sem = new Semaphore(1);
		new IncThread("Increment", sem);
		new DecThread("Decrement", sem);
	}
}

class IncThread implements Runnable {
	String name;
	Semaphore sem;

	public IncThread(String name, Semaphore sem) {
		this.name = name;
		this.sem = sem;

		new Thread(this).start();
	}

	public void run() {
		System.out.println("Starting " + name);

		try {
			System.out.println(name + " is waiting for a permit.");
			sem.acquire();
			System.out.println(name + " gets a permit.");

			for (int i = 0; i < 5; i++) {
				Shared.count++;
				System.out.println(name + ": " + Shared.count);

				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(name + " releases the permit.");
		sem.release();
	}
}

class DecThread implements Runnable {
	String name;
	Semaphore sem;

	public DecThread(String name, Semaphore sem) {
		this.name = name;
		this.sem = sem;

		new Thread(this).start();
	}

	public void run() {
		System.out.println("Starting " + name);
		try {
			System.out.println(name + " is waiting for a permit.");
			sem.acquire();
			System.out.println(name + " gets a permit.");

			for (int i = 0; i < 5; i++) {
				Shared.count--;
				System.out.println(name + ": " + Shared.count);

				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(name + " releases the permit.");
		sem.release();
	}
}

class Shared {
	static int count = 0;
}