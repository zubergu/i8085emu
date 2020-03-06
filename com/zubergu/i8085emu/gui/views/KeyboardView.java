package com.zubergu.i8085emu.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class KeyboardView extends JFrame {

	private JButton enableButton = new JButton("Keyboard disabled");

	public KeyboardView() {
		setTitle("Keyboard View");
		setSize(200, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initializeButtonAction();
		initializeFocusListener();
	}

	private void initializeFocusListener() {
		this.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				enableButton.setText("KEYBOARD ENABLED");

			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				enableButton.setText("KEYBOARD DISABLED");

			}

		});

	}

	private void initializeButtonAction() {
		enableButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				requestFocus();
			}

		});
		this.add(enableButton);

	}

	public void setKeyboardListener(KeyListener keyListener) {
		super.addKeyListener(keyListener);
	}

}
