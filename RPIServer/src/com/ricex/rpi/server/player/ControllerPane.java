package com.ricex.rpi.server.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.server.ClientPlayerModule;
import com.ricex.rpi.server.client.ClientChangeEvent;
import com.ricex.rpi.server.client.ClientChangeListener;
import com.ricex.rpi.server.client.RPIClient;

/** The pane for the window controls
 * 
 * @author Mitchell
 *
 */

public class ControllerPane extends JPanel implements ClientChangeListener<RPIClient>, ActionListener, ActiveClientListener {

	/** Button to play the selected video / playlist */
	private JButton butPlay;
	
	/** Button to pause/resume the currently playing video */
	private JButton butPause;
	
	/** Button to stop the currently playing video */
	private JButton butStop;
	
	/** Button to seek the currently playing video to the left by 30 secconds */
	private JButton butSeekLeft;
	
	/** Button to seek the currently playing video to the left by 600 seconds */
	private JButton butSeekLeftFast;
	
	/** Button to seek the currently playing video to the right by 30 seconds */
	private JButton butSeekRight;
	
	/** Button to seek the currently playing video to the right by 600 seconds */
	private JButton butSeekRightFast;
	
	/** Button to advance ahead to the next chapter */	
	private JButton butNextChapter;
	
	/** Button to go back to the previous chapter */
	private JButton butLastChapter;
	
	public ControllerPane() {
		
		//create the buttons 
		butPlay = new JButton("Play");
		butPause = new JButton("Pause");
		butStop = new JButton("Stop");		
		butSeekLeft = new JButton("<");
		butSeekLeftFast = new JButton("<<");		
		butSeekRight = new JButton(">");
		butSeekRightFast = new JButton(">>");		
		butLastChapter = new JButton("|<<");
		butNextChapter = new JButton(">>|");
		
		//add the action listeners
		butPlay.addActionListener(this);
		butPause.addActionListener(this);
		butStop.addActionListener(this);
		butSeekLeft.addActionListener(this);
		butSeekLeftFast.addActionListener(this);
		butSeekRight.addActionListener(this);
		butSeekRightFast.addActionListener(this);
		butLastChapter.addActionListener(this);
		butNextChapter.addActionListener(this);
		
		//add all the buttons
		add(butPlay);
		add(butPause);
		add(butStop);
		add(butSeekLeft);
		add(butSeekLeftFast);
		add(butSeekRight);
		add(butSeekRightFast);
		add(butLastChapter);
		add(butNextChapter);		
		
	}

	/** Update the status of the buttons depending on the status of the active client
	 * 
	 */
	
	@Override
	public void clientChanged(ClientChangeEvent<RPIClient> changeEvent) {
		if (changeEvent.getEventType() == ClientChangeEvent.EVENT_STATUS_CHANGE) {
			//the active clients status has changed, lets update the player buttons
			RPIStatus status = changeEvent.getSource().getStatus();
			disableButtonsStatus(status);
		}
	}
	
	/** Disables and enables buttons based upon the given status
	 * 
	 * @param status
	 */
	
	private void disableButtonsStatus(RPIStatus status) {
		switch (status.getStatus()) {		
		case RPIStatus.IDLE:					
			butStop.setEnabled(false);
			butPause.setEnabled(false);					
			butSeekLeft.setEnabled(false);
			butSeekLeftFast.setEnabled(false);					
			butSeekRight.setEnabled(false);
			butSeekRightFast.setEnabled(false);					
			butLastChapter.setEnabled(false);
			butNextChapter.setEnabled(false);					
			break;
			
		case RPIStatus.PAUSED:
			butPause.setText("Resume");
			butStop.setEnabled(true);
			butPause.setEnabled(true);					
			butSeekLeft.setEnabled(true);
			butSeekLeftFast.setEnabled(true);					
			butSeekRight.setEnabled(true);
			butSeekRightFast.setEnabled(true);					
			butLastChapter.setEnabled(true);
			butNextChapter.setEnabled(true);
			break;
			
		case RPIStatus.PLAYING:	
			butPause.setText("Pause");
			butStop.setEnabled(true);
			butPause.setEnabled(true);					
			butSeekLeft.setEnabled(true);
			butSeekLeftFast.setEnabled(true);					
			butSeekRight.setEnabled(true);
			butSeekRightFast.setEnabled(true);					
			butLastChapter.setEnabled(true);
			butNextChapter.setEnabled(true);
			break;				
		}
	}
	
	/** Disables all buttons 
	 * 
	 */
	
	private void disableAllButtons() {
		butPlay.setEnabled(false);
		butStop.setEnabled(false);
		butPause.setEnabled(false);					
		butSeekLeft.setEnabled(false);
		butSeekLeftFast.setEnabled(false);					
		butSeekRight.setEnabled(false);
		butSeekRightFast.setEnabled(false);					
		butLastChapter.setEnabled(false);
		butNextChapter.setEnabled(false);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//check to make sure an active client exists
		if (RPIPlayer.getInstance().activeClientExists()) {
			ClientPlayerModule playerModule = RPIPlayer.getInstance().getActiveClient().getPlayerModule();
			Object source = e.getSource();
			
			if (source.equals(butPlay)) {
				//TODO: determine how to implement play
			}
			else if (source.equals(butPause)) {
				playerModule.pause();
			}
			else if (source.equals(butStop)) {
				playerModule.stop();
			}
			else if (source.equals(butSeekLeft)) {
				playerModule.seekBackwardSlow();
			}
			else if (source.equals(butSeekLeftFast)) {
				playerModule.seekBackwardFast();
			}
			else if (source.equals(butSeekRight)) {
				playerModule.seekForwardSlow();
			}
			else if (source.equals(butSeekRightFast)) {
				playerModule.seekForwardFast();
			}
			else if (source.equals(butLastChapter)) {
				playerModule.previousChapter();
			}
			else if (source.equals(butNextChapter)) {
				playerModule.nextChapter();
			}
		}
		
	}

	@Override
	public void activeClientChanged(RPIClient activeClient) {
		disableButtonsStatus(activeClient.getStatus());		
	}

	@Override
	public void activeClientRemoved() {
		disableAllButtons();
	}
	
	
}
