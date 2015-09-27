import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import processing.core.PApplet;

public class Main extends PApplet
{
	// you shouldn't need to edit any of these variables
	int margin = 300; // margin from sides of window
	final int padding = 35; // padding between buttons and also their width/height
	ArrayList<Integer> trials = new ArrayList<Integer>(); //contains the order of buttons that activate in the test
	int trialNum = 0; //the current trial number (indexes into trials array above)
	int startTime = 0; // time starts when the first click is captured.
	int userX = mouseX; //stores the X position of the user's cursor
	int userY = mouseY; //stores the Y position of the user's cursor
	int finishTime = 0; //records the time of the final click
	int hits = 0; //number of succesful clicks
	int misses = 0; //number of missed clicks

	// You can edit variables below here and also add new ones as you see fit
	int numRepeats = 2; //sets the number of times each button repeats in the test (you can edit this)


	public void draw()
	{
		margin = width/3; //scale the padding with the size of the window
		background(0); //set background to black

		if (trialNum >= trials.size()) //check to see if test is over
		{
			fill(255); //set fill color to white
			//write to screen
			text("Finished!", width / 2, height / 2); 
			text("Hits: " + hits, width / 2, height / 2 + 20);
			text("Misses: " + misses, width / 2, height / 2 + 40);
			text("Accuracy: " + (float)hits*100f/(float)(hits+misses) +"%", width / 2, height / 2 + 60);
			text("Total time taken: " + (finishTime-startTime) / 1000f + " sec", width / 2, height / 2 + 80);
			text("Average time for each button: " + ((finishTime-startTime) / 1000f)/(float)(hits+misses) + " sec", width / 2, height / 2 + 100);			
			return; //return, nothing else to do now test is over
		}

		fill(255); //set fill color to white
		text((trialNum + 1) + " of " + trials.size(), 40, 20); //display what trial the user is on

		for (int i = 0; i < 16; i++)// for all button
			drawButton(i); //draw button

		// you shouldn't need to edit anything above this line! You can edit below this line as you see fit
		
		fill(255, 0, 0); // set fill color to red
		ellipse(userX, userY, 20, 20); //draw user cursor as a circle with a diameter of 20

	}

	public void mousePressed() // test to see if hit was in target!
	{
		if (trialNum >= trials.size())
			return;

		if (trialNum == 0) //check if first click
			startTime = millis();

		if (trialNum == trials.size() - 1) //check if final click
		{
			finishTime = millis();
			//write to terminal
			System.out.println("Hits: " + hits);
			System.out.println("Misses: " + misses);
			System.out.println("Accuracy: " + (float)hits*100f/(float)(hits+misses) +"%");
			System.out.println("Total time taken: " + (finishTime-startTime) / 1000f + " sec");
   			System.out.println("Average time for each button: " + ((finishTime-startTime) / 1000f)/(float)(hits+misses) + " sec");
 	}

		Rectangle bounds = getButtonLocation(trials.get(trialNum));

		// YOU CAN EDIT BELOW HERE IF YOUR METHOD REQUIRES IT (shouldn't need to edit above this line)

		if ((userX > bounds.x && userX < bounds.x + bounds.width) && (userY > bounds.y && userY < bounds.y + bounds.height)) // test to see if hit was within bounds
		{
			System.out.println("HIT! " + trialNum + " " + (millis() - startTime)); // success
			hits++;
		} else
		{
			System.out.println("MISSED! " + trialNum + " " + (millis() - startTime)); // fail
			misses++;
		}

		//can manipulate cursor at the end of a trial if you wish
		//userX = width/2; //example manipulation
		//userY = height/2; //example manipulation
		 
		trialNum++; // Increment trial number
	}

	
	public void updateUserMouse() // YOU CAN EDIT THIS
	{
		// you can do whatever you want to userX and userY (you shouldn't touch mouseX and mouseY)
		userX += mouseX - pmouseX; //add to userX the difference between the current mouseX and the previous mouseX
		userY += mouseY - pmouseY; //add to userY the difference between the current mouseY and the previous mouseY
	}

	
	
	
	
	
	
	
	
	// ===========================================================================
	// =========SHOULDN'T NEED TO EDIT ANYTHING BELOW THIS LINE===================
	// ===========================================================================

	public void setup()
	{
		size(900,900); // set the size of the window
		noCursor(); // hides the system cursor (can turn on for debug, but should be off otherwise!)
		noStroke(); //turn off all strokes, we're just using fills here (can change this if you want)
		textFont(createFont("Arial",16));
		textAlign(CENTER);
		frameRate(120);
		ellipseMode(CENTER); //ellipses are drawn from the center (BUT RECTANGLES ARE NOT!)
		// ====create trial order======
		for (int i = 0; i < 16; i++)
			// number of buttons in 4x4 grid
			for (int k = 0; k < numRepeats; k++)
				// number of times each button repeats
				trials.add(i);

		Collections.shuffle(trials); // randomize the order of the buttons
		System.out.println("trial order: " + trials);
	}

	public Rectangle getButtonLocation(int i)
	{
		double x = (i % 4) * padding * 2 + margin;
		double y = (i / 4) * padding * 2 + margin;

		return new Rectangle((int)x, (int)y, padding, padding);
	}
	
	public void drawButton(int i)
	{
		Rectangle bounds = getButtonLocation(i);

		if (trials.get(trialNum) == i) // see if current button is the target
			fill(0, 255, 255); // if so, fill cyan
		else
			fill(200); // if not, fill gray

		rect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public void mouseMoved() // Don't edit this
	{
		updateUserMouse();
	}

	public void mouseDragged() // Don't edit this
	{
		updateUserMouse();
	}

}
