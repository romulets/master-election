package masterelection;

public class App {

	public static void main(String[] args) throws InterruptedException {
		Instance instance = new Instance();
		instance.run();
		instance.join();
	}
	
}
