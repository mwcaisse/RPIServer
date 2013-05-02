package com.ricex.rpi.server.player;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/** Will probally need to change this in future, but temporary for now
 * 
 *  Contains the buttons on the side for interacting with the movie list
 *  
 * @author Mitchell
 *
 */

public class ButtonPane extends VBox {
	
	/** Button to start playing the selected movie in the list view */
	private Button butStart;	
	
	/** Button to pause / resume the playing movie */
	private Button butPlay;
	
	/** Button to stop playing the current movie */
	private Button butStop;
	
	/** Button for seeking to the left 30 seconds */
	private Button butSeekLeft;
	
	/** Button for seeking to the left 600 seconds */
	private Button butSeekLeftFast;
	
	/** Button for seeking to the right 30 seconds */
	private Button butSeekRight;
	
	/** Button for seeking to the right 600 secconds */
	private Button butSeekRightFast;
	
	/** Creates a new ButtonPane for controlling the movies */
	
	public ButtonPane() {
		
		butStart = new Button("Start");
		butPlay = new Button("Play");
		butStop = new Button("Stop");
		butSeekLeft = new Button("<");
		butSeekLeftFast = new Button("<<");
		butSeekRight = new Button(">");
		butSeekRightFast = new Button(">>");
		
		butStart.setPrefWidth(75);
		butPlay.setPrefWidth(75);
		butStop.setPrefWidth(75);
		butSeekLeft.setPrefWidth(75);
		butSeekLeftFast.setPrefWidth(75);
		butSeekRight.setPrefWidth(75);
		butSeekRightFast.setPrefWidth(75);
		
		setPrefSize(100, 10);
		setPadding(new Insets(15, 12, 15, 12));
		setSpacing(10); // spacing between the children
		
		getChildren().add(butStart);
		getChildren().add(butPlay);
		getChildren().add(butStop);
		getChildren().add(butSeekLeft);
		getChildren().add(butSeekLeftFast);
		getChildren().add(butSeekRight);
		getChildren().add(butSeekRightFast);		
		
	}
}
