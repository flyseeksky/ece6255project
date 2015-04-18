package edu.cmu.sphinx.projectTest;
import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class Controller{
	public LiveSpeechRecognizer recognizer;
	public Configuration configuration ; 
	private static final String GRAMMAR_PATH =
			System.getProperty("user.dir") + 
			"/src/main/java/edu/cmu/sphinx/projectTest/";
	
	public Controller() throws IOException {		
		configuration = new Configuration();
		 
		// Set path to acoustic model.
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		// Set path to dictionary.
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		// Set Grammar model 
		configuration.setGrammarPath(GRAMMAR_PATH);
		configuration.setUseGrammar(true);
	    configuration.setGrammarName("commands.grxml");
		// Set language model.
	    //configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
	    recognizer = new LiveSpeechRecognizer(configuration);
	}
	public String answerYesNo(LiveSpeechRecognizer recognizer){
		System.out.println("Are you sure you want to exit the program?");
		this.recognizer.startRecognition(true);
		String answer = "" ; 
		while(!answer.equals("yes") && !answer.equals("no") ){
			SpeechResult result = recognizer.getResult();
			answer = result.getHypothesis();
			System.out.println(answer);
		}
		recognizer.stopRecognition();  
		return answer;
	}


}
