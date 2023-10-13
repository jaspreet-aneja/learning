package MultiThreading.learning;

import java.util.concurrent.Semaphore;

public class MTSemaphoreProducerConsumer {
	public static void main(String[] args) {
		SharedResource sharedResource = new SharedResource();
		
		new ProducerSem(sharedResource);
		new ConsumerSem(sharedResource);
	}
}

class ConsumerSem implements Runnable {
	SharedResource sharedResource;

	public ConsumerSem(SharedResource sharedResource) {
		this.sharedResource = sharedResource;
		new Thread(this, "Producer").start();
	}

	public void run() {
		for(int i=0; i<5; i++) {
			sharedResource.get();
		}
	}
}

class ProducerSem implements Runnable {
	SharedResource sharedResource;

	public ProducerSem(SharedResource sharedResource) {
		this.sharedResource = sharedResource;
		new Thread(this, "Consumer").start();
	}
	
	public void run() {
		for(int i=0; i<5;i++) {
			sharedResource.put();
		}
	}
}

class SharedResource {
	int data = 0;
	
	static Semaphore semConsumer = new Semaphore(0);
	static Semaphore semProducer = new Semaphore(1);
	
	void get() {
		System.out.println("About to acquire permit to consume data.");
		try {
			semConsumer.acquire();
			System.out.println("Permit acquired to consume.");
			System.out.println("Consumed Data: " + data);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Data consumed, letting producer know.");
		semProducer.release();
	}
	
	void put() {
		System.out.println("About to acquire permit to produce data.");
		try {
			semProducer.acquire();
			System.out.println("Permit acquired to produce.");
			data++;
			System.out.println("Produced Data: " + data);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Data produced, letting consumer know.");
		semConsumer.release();
	}
}
