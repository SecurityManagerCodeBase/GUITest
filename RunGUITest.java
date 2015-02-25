import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

//to run the GUI fuzzer on a GUI, you first need to set the time to execute at the bottom of the main file.  Then you need to set the pixel bounds of the application in the moveMouse function.
public class RunGUITest {

	static int screenHeight = 0;
	static int screenWidth = 0;
	
	public static void main(String[] args) {
		//System.out.println("random key start: "+KeyEvent.KEY_FIRST);
		//System.out.println("random end key: "+KeyEvent.KEY_LAST);
		//Math.random();
		Robot rob = null;
		try {
			 rob = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("unable to make the robot!!");
			System.exit(1);
		}
		Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = (int)screenDim.getHeight();
		screenWidth = (int)screenDim.getWidth();
		//System.out.println("screen hieght: "+screenDim.getHeight());
		//System.out.println("screen width: "+screenDim.getWidth());
		rob.setAutoDelay(100); //waiting 0.1 seconds between actions
		rob.setAutoWaitForIdle(true); //waiting until action finishes to do something else
 		Random rand = new Random();
 		System.out.println("waiting 5 seconds before starting!!");
 		rob.delay(5000); //wait 5 seconds before starting
 		int count = 0;
 		long startTime = System.currentTimeMillis();
 		long finalTime = startTime + 1800000; //running until action finishes after 30 minutes
 		System.out.println("Staring the random actions!!");
                System.out.println("Running for 30 minutes");
 	   while(System.currentTimeMillis() < finalTime){
 		moveMouse(rob,rand);
 		int numberOfActions = rand.nextInt(10);
 		for(int i = 0; i < numberOfActions; i++){
 	      performAction(rob, rand);  
 		}
 		count++;
 		if(count > 10){
 			count = 0;
 			rob.delay(1000); //wait one second
 		}
 	  }
          System.out.println("finished!!");
	}
	
	public static void moveMouse(Robot rob, Random rand){
                int applicationMaxX = 1171;
                int applicationMaxY = 808;
                int applicationMinX = 70;
                int applicationMinY = 30;
		int width = rand.nextInt(applicationMaxX - applicationMinX +1);
		width = width + applicationMinX;
		int height = rand.nextInt(applicationMaxY - applicationMinY+1);
		height=height + applicationMinY;
		rob.mouseMove(width, height);
	}
	
	public static void performAction(Robot rob, Random rand){
		double takeAction = rand.nextDouble();
		//favor mouse presses
		if(takeAction < .7){
			//mouse press event
			double whichMouseButton = rand.nextDouble();
			//favor normal clicking
			if(whichMouseButton< 0.7){
				rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
			else if(whichMouseButton< 0.9){
				rob.mousePress(InputEvent.BUTTON2_DOWN_MASK);
				rob.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
			}
			else {
				rob.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				rob.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			}
		}
		//if not a mouse press, the do a key press
		else{
		 pressKey(rob, rand);
		}
	
	}
	
	public static void pressKey(Robot rob, Random rand){
	  double keyType = rand.nextDouble();
	  if(keyType < .6){
		//pressing a letter key
		int key = getAlphabetKey(rand);
		if(rand.nextInt(2) == 0){
			//pressing key in lower case form
			rob.keyPress(key);
			rob.keyRelease(key);
		}else{
			rob.keyPress(KeyEvent.VK_SHIFT);
			rob.keyPress(key);
			rob.keyRelease(key);
			rob.keyRelease(KeyEvent.VK_SHIFT);
		}
	  } else if (keyType < .8){
		  int numKey = getNumberKey(rand);
		  rob.keyPress(numKey);
		  rob.keyRelease(numKey);
	  } else{
		  //pressing sapce bar
		  rob.keyPress(KeyEvent.VK_SPACE);
		  rob.keyRelease(KeyEvent.VK_SPACE);
	  }
	  
	  
	}

	public static int getAlphabetKey(Random rand){
		int choice = rand.nextInt(26);
		return 65+choice; //getting ascii value
	}
	
	public static int getNumberKey(Random rand){
		int choice = rand.nextInt(10);
		return 48+choice; //getting ascii value
	}
}
