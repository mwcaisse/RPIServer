package com.ricex.rpi.server.imbdparser;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ricex.rpi.server.player.view.video.VideoTreeView;

/**  IMDB Video Tree View
 * 
 *  Adds movie information from IMDB to the plain Video Tree View
 * 
 * @author Mitchell
 *
 */

public class IMDBVideoTreeView extends VideoTreeView {

	/** The side panel that will be used to display the movie information */
	protected JPanel sidePanel;
	
	/** The label to display the name of the movie */
	protected JLabel labMovieName;
	
	/** The label to display the description of the movie */
	protected JLabel labMovieDescription;
	
	/** Initializes all of the components needed to display the movie information for the selected
	 *  video
	 */
	
	public IMDBVideoTreeView() {
		sidePanel = new JPanel();
		
		labMovieName = new JLabel("Movie Name!!");
		
		labMovieDescription = new JLabel("<html>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus id velit ipsum. Curabitur suscipit nibh sit amet quam varius pretium. Donec pulvinar, augue eu euismod sollicitudin, augue erat accumsan erat, et ornare elit sapien mattis ligula. Aliquam luctus, magna a ullamcorper laoreet, odio turpis venenatis elit, nec dignissim lacus augue a lacus. Morbi aliquam pellentesque aliquam. Praesent dignissim malesuada facilisis. Phasellus dapibus, nulla tincidunt molestie scelerisque, velit turpis dictum metus, ac molestie nulla nisi vulputate purus. Donec sit amet fringilla tortor, quis tincidunt metus. Nulla tincidunt, purus non ullamcorper blandit, odio sapien dignissim tortor, eu viverra justo quam nec felis. </html>");
		
		sidePanel.add(labMovieName);
		sidePanel.add(labMovieDescription);
		
		sidePanel.setPreferredSize(new Dimension(300, 100));
		
		add(sidePanel, BorderLayout.EAST);
	}
	
}
