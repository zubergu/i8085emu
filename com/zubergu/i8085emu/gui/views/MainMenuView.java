package com.zubergu.i8085emu.gui.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class MainMenuView extends JFrame {

	private JMenuBar menuBar;
	private JToolBar debugToolbar;
	private JMenu roms;
	private JMenu rams;
	private JTextArea console;
	private JTextField numberOfInstructions;

	private JButton runButton;
	private JButton pauseButton;
	private JButton debugButton;
	private JButton stepButton;
	private JButton resetButton;

	public MainMenuView() {
		createMenuBar();
		createDebugToolbar();
		createConsole();
		setTitle("Main menu");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void createMenuBar() {
		menuBar = new JMenuBar();
		roms = new JMenu("ROMs");
		rams = new JMenu("RAMs");

		menuBar.add(roms);
		menuBar.add(rams);
		this.setJMenuBar(menuBar);

	}

	private void createDebugToolbar() {
		debugToolbar = new JToolBar();

		runButton = new JButton("Run");
		runButton.setEnabled(true);
		pauseButton = new JButton("Pause");
		pauseButton.setEnabled(false);
		debugButton = new JButton("Debug");
		debugButton.setEnabled(true);
		stepButton = new JButton("Step");
		stepButton.setEnabled(false);
		resetButton = new JButton("Reset");
		resetButton.setEnabled(true);

		numberOfInstructions = new JTextField();
		numberOfInstructions.setText("1");
		numberOfInstructions.setEnabled(true);

		debugToolbar.add(runButton);
		debugToolbar.add(pauseButton);
		debugToolbar.add(debugButton);
		debugToolbar.add(stepButton);
		debugToolbar.add(resetButton);
		debugToolbar.add(numberOfInstructions);

		this.add(debugToolbar, BorderLayout.PAGE_START);
	}

	private void createConsole() {
		this.console = new JTextArea();
		console.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(console);
		console.setText("EMULATION CONSOLE\n");

		add(scrollPane, BorderLayout.CENTER);

	}

	public void addRomToMenu(String deviceName, ActionListener menuListener) {
		JMenuItem item = new JMenuItem(deviceName);
		item.addActionListener(menuListener);
		roms.add(item);
	}

	public void addRamToMenu(String deviceName, ActionListener menuListener) {
		JMenuItem item = new JMenuItem(deviceName);
		item.addActionListener(menuListener);
		rams.add(item);
	}

	public void setRunButtonActionListener(ActionListener listener) {
		for (ActionListener l : runButton.getActionListeners()) {
			runButton.removeActionListener(l);
		}

		runButton.addActionListener(listener);
	}

	public void setPauseButtonActionListener(ActionListener listener) {
		for (ActionListener l : pauseButton.getActionListeners()) {
			pauseButton.removeActionListener(l);
		}

		pauseButton.addActionListener(listener);
	}

	public void setDebugButtonActionListener(ActionListener listener) {
		for (ActionListener l : debugButton.getActionListeners()) {
			debugButton.removeActionListener(l);
		}

		debugButton.addActionListener(listener);
	}

	public void setStepButtonActionListener(ActionListener listener) {
		for (ActionListener l : stepButton.getActionListeners()) {
			stepButton.removeActionListener(l);
		}

		stepButton.addActionListener(listener);
	}

	public void setResetButtonActionListener(ActionListener listener) {
		for (ActionListener l : resetButton.getActionListeners()) {
			resetButton.removeActionListener(l);
		}

		resetButton.addActionListener(listener);
	}

	public void sendTextToConsole(String message) {
		console.append(message + "\n");
	}
	
	public void showBreakpointMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Breakpoint message", JOptionPane.INFORMATION_MESSAGE);
	}

	public Integer getNumberOfInstructionsPerStep() {
		Integer instructions = null;
		try {
			instructions = Integer.parseInt(numberOfInstructions.getText());
		} catch (NumberFormatException ex) {
			sendTextToConsole("Wrong number of instructions to step is set.");
		}
		return instructions;
	}

	public JButton getPauseButton() {
		return pauseButton;
	}

	public JButton getRunButton() {
		return runButton;
	}

	public JButton getDebugButton() {
		return debugButton;
	}

	public JButton getStepButton() {
		return stepButton;
	}

	public JButton getResetButton() {
		return resetButton;
	}

}
