package com.zubergu.i8085emu.gui.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayView extends JFrame {

	private BufferedImage frame;
	private JPanel imgPanel;

	private Dimension displayDimension;

	public DisplayView(Dimension displayDimension) {

		setTitle("Display");
		this.displayDimension = displayDimension;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imgPanel = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(frame, 0, 0, this);
			}

		};
		
		imgPanel.setSize(displayDimension);
	    getContentPane().setPreferredSize(displayDimension);
	    pack();
		add(imgPanel);

	}

	public void updateScreen(BufferedImage image) {
		frame = image;
		imgPanel.revalidate();
		imgPanel.repaint();
	}
}
