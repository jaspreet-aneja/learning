package MultiThreading.learning;

public class MTProducerConsumerWrong {
	public static void main(String[] args) {
		QueueW queue = new QueueW();

		new ProducerW(queue);
		new ConsumerW(queue);
	}
}

class QueueW {
	int n;

	synchronized void put(int n) {
		System.out.println("Put " + n);
		this.n = n;
		MTUtility.sleep(0.3, false);
	}

	synchronized int get() {
		System.out.println("Got " + n);
		MTUtility.sleep(0.3, false);
		return n;
	}
}

class ProducerW implements Runnable {
	QueueW queue;

	public ProducerW(QueueW queue) {
		this.queue = queue;
		new Thread(this, "Producer").start();
	}

	public void run() {
		int i = 0, count = 0;
		while (count < 10) {
			queue.put(i++);
			count++;
		}
	}
}

class ConsumerW implements Runnable {
	QueueW queue;

	public ConsumerW(QueueW queue) {
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