package MultiThreading.learning;

public class MTProducerConsumer {
	public static void main(String[] args) {
		Queue queue = new Queue();

		new Producer(queue);
		new Consumer(queue);
	}
}

class Queue {
	int n;
	boolean valueAvailable = false;

	synchronized void put(int n) {
		while (valueAvailable) {
			try {
				wait();
			} catch (Exception e) {
				System.out.println("Put interrupted.");
			}
		}

		valueAvailable = true;
		System.out.println("Put " + n);
		this.n = n;
		MTUtility.sleep(0.3, false);
		notify();
	}

	synchronized int get() {
		while (!valueAvailable) {
			try {
				wait();
			} catch (Exception e) {
				System.out.println("Get interrupted");
			}
		}

		valueAvailable = false;
		System.out.println("Get " + n);
		notify();
		MTUtility.sleep(0.3, false);
		return n;
	}
}

class Producer implements Runnable {
	Queue queue;

	public Producer(Queue queue) {
		this.queue = queue;
		new Thread(this, "Producer").start();
	}

	public void run() {
		int count = 0;
		while (count < 10) {
			queue.put(count);
			count++;
		}
	}
}

class Consumer implements Runnable {
	Queue queue;

	public Consumer(Queue queue) {
		this.queue = queue;
		new Thread(this, "Consumer").start();
	}

	public void run() {
		int count = 0;
		while (count < 10) {
			queue.get();
			count++;
		}
	}
}