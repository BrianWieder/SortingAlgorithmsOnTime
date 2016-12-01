
public class Main {
	static Window winder;
	static LoadingScreen ls;
	
	public static void main(String[] args){ 
		 winder = new Window();
	}
	
	public static void startTesting(int numTests, String outputFile, int lengthOfData){
		winder.closeWinder();
		
		ExperimentalThread et = new ExperimentalThread("Experimental Thread", numTests, lengthOfData, outputFile);
		et.start();
	}	
}