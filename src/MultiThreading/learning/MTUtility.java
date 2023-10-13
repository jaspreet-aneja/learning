package MultiThreading.learning;

public class MTUtility {

	public static void sleep(double seconds, boolean skipSleep) {
		try{
			if(!skipSleep) {
				Thread.sleep((int)(seconds * 1000));
			}
		} catch(InterruptedException ex) {
			System.out.println("sleep interrupted.");
		}
	}
}
