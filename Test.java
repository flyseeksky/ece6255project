package edu.cmu.sphinx.projectTest;
import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class Test {
	private static final String GRAMMAR_PATH = System.getProperty("user.dir") + 
			"/src/main/java/edu/cmu/sphinx/projectTest/";
		        //"resource:/edu/cmu/sphinx/demo/projectTest/";
	
	private static String answerYesNo(LiveSpeechRecognizer recognizer){
		System.out.println("Are you sure you want to exit the program?");
		recognizer.startRecognition(true);
		String answer = "" ; 
		while(!answer.equals("yes")  ){
			SpeechResult result = recognizer.getResult();
			answer = result.getHypothesis();
			System.out.println(answer);
		}
		recognizer.stopRecognition();  
		return answer;
	}
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Configuration configuration = new Configuration();

		 
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
	
		LiveSpeechRecognizer recognizer;
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
			
			// Start recognition process pruning previously cached data.
			System.out.println("Start Recognition");
			recognizer.startRecognition(true);
			//System.out.println("Current directory " + System.getProperty("user.dir"));
			
			while( true ){
				
				System.out.println("Get Results");
				SpeechResult result = recognizer.getResult();
				String utterance = result.getHypothesis();
				System.out.println(utterance); 
				 
				if (utterance.startsWith("exit")){
					    recognizer.stopRecognition();
					    String answer = answerYesNo(recognizer) ;
					    recognizer.startRecognition(true);
					    if( answer.equals("yes")){
					    	break;
					    }
				}
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
			}
			// Pause recognition process. It can be resumed then with startRecognition(false).
			recognizer.stopRecognition();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	
	}

}
