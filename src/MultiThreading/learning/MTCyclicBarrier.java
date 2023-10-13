package MultiThreading.learning;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier supplied by concurrent API is used for situations, when a set
 * of two or more threads must wait at a predetermined execution point until all
 * threads in the set have reached that point. It enables you to define a
 * synchronization object that suspends until the specified number of threads
 * has reached the barrier point.
 * 
 * @author Jaspreet
 *
 */
public class MTCyclicBarrier {
	public static void main(String[] args) {

		// Specify the number of threads that you will be waiting for.
		// When each thread reaches the barrier, have it call await() on that object.
		// This will pause execution of the thread until all of the other threads also
		// call await().
		// Once the specified number of threads has reached the barrier, await() will
		// return and execution will resume.
		// Also, if you have specified an action, then that thread is executed.
		CyclicBarrier cBar = new CyclicBarrier(3, new BarAction());

		System.out.println("Starting.");

		new CBMyThread(cBar, "A");
		new CBMyThread(cBar, "B");
		new CBMyThread(cBar, "C");
		
		// Cyclic barrier can be reused, because it will release waiting threads each
		// time the specified number of threads calls await().
		// So, if more threads are added, it will work like a batch.
		new CBMyThread(cBar, "X");
		new CBMyThread(cBar, "Y");
		new CBMyThread(cBar, "Z");
	}
}

class CBMyThread implements Runnable {
	CyclicBarrier cBar;
	String name;

	public CBMyThread(CyclicBarrier cBar, String name) {
		this.cBar = cBar;
		this.name = name;

		new Thread(this).start();
	}

	public void run() {
		System.out.println(name);

		try {
			cBar.await();
		} catch (BrokenBarrierException bbe) {
			System.out.println(bbe);
		} catch (InterruptedException iExc) {
			System.out.println(iExc);
		}
	}
}

class BarAction implements Runnable {
	public void run() {
		System.out.println("Barrier Reached.");
	}
}
