package edu.cmu.sphinx.projectTest;

import javax.swing.*;

import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.Result;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UI implements ActionListener{
	private JFrame frame;
	public JPanel btnPanel;
	private JButton startBtn;
	private JButton stopBtn;
	private JTextArea text;
	public boolean running = false;
	public Controller cnt;
	public Sandbox box;
	public Thread th;
	
	public void setupUI() throws IOException
	{
		frame = new JFrame();
		btnPanel = new JPanel();
		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		text = new JTextArea(15,20);
		JScrollPane spane = new JScrollPane(text);
		
		text.setEditable(false);
		stopBtn.setEnabled(false);
		
		startBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		
		btnPanel.setLayout(new BorderLayout());
		btnPanel.add(startBtn, BorderLayout.WEST);
		btnPanel.add(stopBtn, BorderLayout.EAST);
		frame.getContentPane().add(spane, BorderLayout.CENTER);
		frame.getContentPane().add(btnPanel, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setVisible(true);
		
		box = new Sandbox();
		cnt = new Controller();
	}
	
	public void appendText(String str)
	{
		text.append(str);
	}
	
	public void setButtonState(boolean running)
	{
		startBtn.setEnabled(!running);
		stopBtn.setEnabled(running);
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == stopBtn )
		{
//			cnt.Stop();
			running = false;
			setButtonState(false);
		}
		else
		{
			setButtonState(true);
			System.out.println("start clicked!");
			running = true;
			th.start();
		}
	}
	
	public class Sandbox implements Runnable, ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if( e.getSource() == stopBtn )
			{
				running = false;
			}
			else
			{
				running = true;
				try {
					go();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("Sandbox running!");
			try {
				go();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void go() throws IOException
		{
			System.out.println("go is running");
			startBtn.setEnabled(false);
			stopBtn.setEnabled(true);
			String utterance;
			while( running )
			{
				
				cnt.recognizer.startRecognition(true);
				text.append("Start listenging! Please speak ... ");
				SpeechResult result = cnt.recognizer.getResult();
				utterance = result.getHypothesis();
				cnt.recognizer.stopRecognition();
				text.append("You've just spoke: " + utterance + "\n\n");
				if( !proessCMD(utterance) )
				{
					running = false;
//					cnt.Stop();
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
	
	
	public static void main(String[] args) throws IOException {
		UI app = new UI();
		app.setupUI();
		
		app.th = new Thread(app.box);
	}

}
