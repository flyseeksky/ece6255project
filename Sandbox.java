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
		while( ui.getRunning() )
		{
			
			ui.getController().recognizer.startRecognition(true);
			ui.appendText("Start listenging! Please speak ... ");
			SpeechResult result = ui.getController().recognizer.getResult();
			utterance = result.getHypothesis();
			ui.getController().recognizer.stopRecognition();
			ui.appendText("You've just spoke: " + utterance + "\n\n");
			if( !proessCMD(utterance) )
			{
				ui.setRunning(false);
				break;
			}
			
		}
		System.out.println("Stopped running! Click start button to start again!\n");
	}
	
	private boolean proessCMD(String utterance) throws IOException {
		// TODO Auto-generated method stub
	    if (utterance.contains("open skype")) {
	    	Runtime.getRuntime().exec("open -a Skype");  	 
	    }
	    else if (utterance.contains("close skype")) {
	    	Runtime.getRuntime().exec("pkill Skype");  
	    }
	    else if (utterance.contains("open safari")) {
	    	Runtime.getRuntime().exec("open -a Safari");  
	    }
	    else if (utterance.contains("close safari")) {
	    	Runtime.getRuntime().exec("pkill Safari");  
	    }
	    else if (utterance.contains("open itunes")) {
	    	Runtime.getRuntime().exec("open -a iTunes");  
	    }
	    else if (utterance.contains("close itunes")) {
	    	Runtime.getRuntime().exec("pkill iTunes");  
	    }
		return true;
	}

	
}
