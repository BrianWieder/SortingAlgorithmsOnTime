import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LoadingScreen {

	private JProgressBar progressBar;
	private JFrame f;
	
	public LoadingScreen(int max){
		f = new JFrame("Effect of Sorting Algorithm on Time");
		JPanel p = new JPanel();
		progressBar = new JProgressBar();
		f.setSize(500, 100);
		progressBar.setSize(f.getSize().width, 50);
		progressBar.setMinimum(0);
		progressBar.setMaximum(max);
		p.add(progressBar);
		f.add(p);
		f.setVisible(true);
	}
	
	public void setProgressBar(int value){
		progressBar.setValue(value);
	}
	
	public int getProgress(){
		return progressBar.getValue();
	}
	
	public void closeWinder(){
		f.dispose();
	}
	
}
