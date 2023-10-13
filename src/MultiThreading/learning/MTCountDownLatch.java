package MultiThreading.learning;

import java.util.concurrent.CountDownLatch;

/**
 * This will let the thread wait until one or more events have occurred. CDL is
 * initially created with count of number of events that must occur before the
 * latch is released. Each time event happens, the count is decremented. When
 * the count reached zero, latch opens.
 * 
 * @author Jaspreet
 *
 */
public class MTCountDownLatch {
	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(5);
		System.out.println("Started.");
		
		new CDLThread(cdl);
		
		try {
			cdl.await();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Done.");
	}
}

class CDLThread implements Runnable {
	CountDownLatch cdl;
	public CDLThread(CountDownLatch cdl) {
		this.cdl = cdl;
		new Thread(this).start();
	}

	public void run() {
		for(int i=0;i<5;i++) {
			System.out.println(i);
			cdl.countDown();
		}
	}
}
