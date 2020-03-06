package com.zubergu.i8085emu.gui.components;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JumpPanel extends JPanel {

	private JButton jumpButton;
	private JTextField jumpAddress;

	public JumpPanel() {
		jumpButton = new JButton("Jump");
		jumpAddress = new JTextField();
		setLayout(new GridLayout(1, 2));
		jumpAddress.setSize(200, 50);
		jumpAddress.setEditable(true);
		add(jumpAddress);
		add(jumpButton);
	}

	public void setJumpButtonActionListener(ActionListener al) {
		jumpButton.addActionListener(al);
	}

	public String getJumpAddressValue() {
		return jumpAddress.getText();
	}

}
