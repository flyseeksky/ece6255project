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
	private boolean running = false;
	private Controller cnt;
	
	// Setup GUI component
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
	
	public JButton getStopButton()
	{ 	return stopBtn; }
	public JButton getStartButto()
	{	return startBtn; }
	
	public void setRunning(boolean running)
	{
		this.running = running;
	}
	
	public boolean getRunning()
	{
		return running;
	}
	
	public Controller getController()
	{
		return cnt;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == stopBtn )
		{
			running = false;
			setButtonState(false);
		}
		else
		{
			setButtonState(true);
			System.out.println("start clicked!");
			running = true;
			// start sandbox thread which runs as background thread
			// and process all the recognition stuff
			new Thread(new Sandbox(this)).start();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		UI app = new UI();
		app.setupUI();
	}

}
