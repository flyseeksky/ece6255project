package edu.cmu.sphinx.projectTest;

import java.io.IOException;

import edu.cmu.sphinx.api.SpeechResult;

public class Sandbox  implements Runnable
{
	public UI ui;
	
	Sandbox(UI ui)
	{
		this.ui = ui;
	}
	@Override
	public void run() {
		System.out.println("Sandbox running!");
		try {
			go();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void go() throws IOException
	{
		System.out.println("go is running");
		ui.getStartButto().setEnabled(false);
		ui.getStopButton().setEnabled(true);
		String utterance;
		boolean firstTime = true;
		while( ui.getRunning() )
		{
			if( firstTime )
			{
				ui.appendText("Loading resource, please wait ...\n");
				firstTime = false;
			}
			ui.getController().recognizer.startRecognition(true);
			ui.appendText("Ready!\nStart listenging! Please speak ... \n");
			SpeechResult result = ui.getController().recognizer.getResult();
			utterance = result.getHypothesis();
			ui.getController().recognizer.stopRecognition();
			ui.appendText("You've just spoke: " + utterance + "\n\n");
			switch ( proessCMD(utterance)  )
			{
			case 0:
				break;
			case 1:
				ui.appendText("Program opened!\n");
				break;
				
			case 2:
				ui.appendText("Program closed!\n");
				break;
				
			case 3:
				ui.appendText("Sorry, I can't understand you!\n");
				break;
			}
			
			ui.appendText("\n\n");
		}
		System.out.println("Stopped running! Click start button to start again!\n");
	}
	
	// return number means different state:
	// 1 -- Successfully opened a program
	// 2 -- Successfully closed a program
	// 3 -- Can't understand
	private int proessCMD(String utterance) throws IOException {
	    if (utterance.contains("open skype")) {
	    	Runtime.getRuntime().exec("open -a Skype"); 
	    	return 1;
	    }
	    else if (utterance.contains("close skype")) {
	    	Runtime.getRuntime().exec("pkill Skype");  
	    	return 2;
	    }
	    else if (utterance.contains("open safari")) {
	    	Runtime.getRuntime().exec("open -a Safari");  
	    	return 1;
	    }
	    else if (utterance.contains("close safari")) {
	    	Runtime.getRuntime().exec("pkill Safari");  
	    	return 2;
	    }
	    else if (utterance.contains("open itunes")) {
	    	Runtime.getRuntime().exec("open -a iTunes"); 
	    	return 1;
	    }
	    else if (utterance.contains("close itunes")) {
	    	Runtime.getRuntime().exec("pkill iTunes");  
	    	return 2;
	    }
	    else
	    {
	    	return 3;
	    }
	}

	
}
