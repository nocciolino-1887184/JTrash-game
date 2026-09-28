package view;

import javax.swing.*;

import controller.GameController;

import java.awt.Font;
import java.awt.Color;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1860979716621182121L;

	/**
	 * Create the panel.
	 */
	public MenuPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		setBounds(0, 0, 1219, 678);
		
		/** questo è il pannello del menù, in oltre avremo
		 *  il pannello del profilo
		 *  il pannello di fine gioco
		 *  il pannello del gioco
		 * 
		 */
		
		
		JLabel title = new JLabel("JTrash");
		title.setBounds(668, 287, 135, 48);
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setFont(new Font("Power Red and Blue", Font.PLAIN, 50));
		add(title);
		
		JLabel subtitle = new JLabel("The game");
		subtitle.setFont(new Font("Power Red and Green", Font.ITALIC, 20));
		subtitle.setBounds(705, 345, 77, 24);
		add(subtitle);
		
		
		
		JLabel photo = new JLabel();
		photo.setBounds(269, 35, 891, 586);
		photo.setIcon(new ImageIcon(MenuPanel.class.getResource("/images/icon.png")));
		add(photo);
	

	}
}
