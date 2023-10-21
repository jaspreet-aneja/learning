package MultiThreading.learning;

import java.util.concurrent.Phaser;

/**
 * MTPhaser used three threads that were all of the same type, that is not a
 * requirement. Each party that uses a phaser can be unique, with each
 * performing some separate task.
 * 
 * It is possible to take control of precisely what happens when a phase advance
 * occurs. To do this, you must override the onAdvance() method. This method is
 * called by the runtime when a Phaser advances from one phase to the next.
 * 
 * protected boolean onAdvance(int phase, int numParties)
 * 
 * phase will contain the current phase number prior to being incremented and
 * numParties will contain the number of registered parties. To terminate the
 * phase, onAdvance() must return true. To keep the phase alive, onAdvance()
 * must return false. The default version of onAdvance() returns true, when
 * there are no registered parties.
 * 
 * Once reason to override onAdvance() is to enable a phaser to execute a
 * specific number of phase and then stop.
 * 
 * @author Jaspreet
 *
 */
public class MTPhaserOnAdvance {
	public static void main(String[] args) {
		MyPhaser phaser = new MyPhaser(1, 5);
		System.out.println("Starting.");

		new MyThreadPhaser(phaser, "A");
		new MyThreadPhaser(phaser, "B");
		new MyThreadPhaser(phaser, "C");

		while (!phaser.isTerminated()) {
			phaser.arriveAndAwaitAdvance();
		}

		System.out.println("The phaser is terminated.");
	}
}

class MyPhaser extends Phaser {
	int noOfPhases;

	public MyPhaser(int parties, int noOfPhases) {
		super(parties);
		this.noOfPhases = noOfPhases - 1;
	}

	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
		System.out.println("Phase " + phase + " completed.");

		return phase == noOfPhases || registeredParties == 0;
	}
}

class MyThreadPhaser implements Runnable {
	Phaser phaser;
	String name;

	public MyThreadPhaser(Phaser phaser, String name) {
		this.phaser = phaser;
		this.name = name;

		phaser.register();
		new Thread(this).start();
	}

	public void run() {
		while (!phaser.isTerminated()) {
			System.out.println("Thread " + name + " beginning phase " + phaser.getPhase());
			phaser.arriveAndAwaitAdvance();

			MTUtility.sleep(0.1, false);
		}
	}
}