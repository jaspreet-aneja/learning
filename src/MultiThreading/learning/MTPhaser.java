package MultiThreading.learning;

import java.util.concurrent.Phaser;

/**
 * Its purpose is to enable the synchronization of threads that represent one or
 * more phases of activity. Phaser works a bit like a CyclicBarrier, except it
 * supports multiple phases. Phaser lets define a synchronization object that
 * waits until a specific phase has completed.
 * 
 * Create a instance of Phaser, next, register one or more parties with Phaser,
 * either by calling register() or by specifying the number of parties in the
 * constructor.
 * 
 * For each registered party, have the phaser wait until all registered parties
 * complete a phase. A party signals this by calling arrive()or
 * arriveAndAwaitAdvance(). After all parties have arrived, the phase is
 * complete and the phase can move on to the next phase, if there is one, or
 * terminate..
 * 
 * @author Jaspreet
 *
 */
public class MTPhaser {

	public static void main(String[] args) {
		Phaser phaser = new Phaser(1); // party count 1 corresponds to main thread
		int currentPhase;

		System.out.println("Starting");

		new MyThreadP(phaser, "A");
		new MyThreadP(phaser, "B");
		new MyThreadP(phaser, "C");

		// Wait for all threads to complete phase one.
		currentPhase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("Phase " + currentPhase + " completed.");

		// wait for other threads
		currentPhase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("Phase " + currentPhase + " completed.");

		// wait for other threads
		currentPhase = phaser.getPhase();
		phaser.arriveAndAwaitAdvance();
		System.out.println("Phase " + currentPhase + " completed.");

		// deregister the main thread
		phaser.arriveAndDeregister();

		if (phaser.isTerminated()) {
			System.out.println("The phaser is terminated.");
		}
	}
}

class MyThreadP implements Runnable {
	Phaser phaser;
	String name;

	public MyThreadP(Phaser phaser, String name) {
		this.phaser = phaser;
		this.name = name;
		phaser.register();
		new Thread(this).start();
	}

	public void run() {
		System.out.println("Thread "+name+" beginning phase one.");
		phaser.arriveAndAwaitAdvance();
		MTUtility.sleep(0.1, false);
		
		System.out.println("Thread "+name+" beginning phase two.");
		phaser.arriveAndAwaitAdvance();
		MTUtility.sleep(0.1, false);
		
		System.out.println("Thread "+name+" beginning phase three.");
		phaser.arriveAndDeregister();
	}
}
