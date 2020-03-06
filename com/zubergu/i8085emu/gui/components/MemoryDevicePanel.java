package com.zubergu.i8085emu.gui.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.zubergu.i8085emu.io.ROM;

public class MemoryDevicePanel extends JPanel {

	public static final int MAX_MEMORY_ROWS = 20;

	private String[] conditionsValues = { "==", "!=", "<", "<=", ">", ">=" };

	private JLabel deviceName;
	private List<JLabel> displayedAddresses = new LinkedList<JLabel>();
	private List<JTextField> displayedValues = new LinkedList<JTextField>();
	private List<JCheckBox> breakpoints = new LinkedList<JCheckBox>();
	private List<JComboBox<String>> conditions = new LinkedList<JComboBox<String>>();
	private List<JTextField> breakpointValues = new LinkedList<JTextField>();
	private JumpPanel jumpPanel;
	private JButton minusButton;
	private JButton plusButton;
	private int startAddress;
	private int size;
	private int currentDisplayStartAddress;

	private Map<Integer, String> addressToValue = new HashMap<Integer, String>();
	private Map<Integer, Boolean> addressToBreakpoint = new HashMap<Integer, Boolean>();
	private Map<Integer, String> addressToCurrentCondition = new HashMap<Integer, String>();
	private Map<Integer, String> addressToBreakpointValue = new HashMap<Integer, String>();

	public MemoryDevicePanel(String deviceName, int startAddress, int size) {
		this.deviceName = new JLabel(deviceName);
		this.startAddress = startAddress;
		this.size = size;
		this.currentDisplayStartAddress = startAddress;
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		add(deviceName, BorderLayout.NORTH);

		JPanel valuesPanel = new JPanel(); // panel for address - value pairs

		valuesPanel.setLayout(new GridLayout(0, 5));
		valuesPanel.add(new JLabel("Address"));
		valuesPanel.add(new JLabel("Value"));
		valuesPanel.add(new JLabel("Breakpoint"));
		valuesPanel.add(new JLabel("Condition"));
		valuesPanel.add(new JLabel("Break value"));

		for (int i = startAddress; i < startAddress + size; i++) {
			addressToValue.put(i, "0");
			addressToBreakpoint.put(i, false);
			addressToCurrentCondition.put(i, "==");
			addressToBreakpointValue.put(i, "");
		}

		for (int i = currentDisplayStartAddress; i < currentDisplayStartAddress
				+ MAX_MEMORY_ROWS; i++) {
			
			JLabel addressLabel = new JLabel(String.format("0x%04X\n", i));
			
			valuesPanel.add(addressLabel);
			displayedAddresses.add(addressLabel);

			JTextField field = new JTextField();
			field.setText(addressToValue.get(i));
			field.setEditable(false);
			field.setHorizontalAlignment(JTextField.RIGHT);

			displayedValues.add(field);
			valuesPanel.add(field);

			JCheckBox breakpoint = new JCheckBox();
			breakpoint.addActionListener(new BreakpointCheckboxItemListener());
			breakpoint.setSelected(addressToBreakpoint.get(i));
			valuesPanel.add(breakpoint);
			breakpoints.add(breakpoint);

			JComboBox<String> conditionBox = new JComboBox<String>(
					conditionsValues);
			conditionBox.addItemListener(new ConditionComboboxItemListener());
			conditionBox.setSelectedItem(addressToCurrentCondition.get(i));
			valuesPanel.add(conditionBox);
			conditions.add(conditionBox);

			JTextField breakpointValueField = new JTextField();
			breakpointValueField.addActionListener(new ConditionValueFieldActionListener());
			breakpointValueField.setText(addressToBreakpointValue.get(i));
			field.setEditable(false);
			valuesPanel.add(breakpointValueField);

			breakpointValues.add(breakpointValueField);

		}

		add(valuesPanel, BorderLayout.CENTER);

		jumpPanel = new JumpPanel();
		add(jumpPanel, BorderLayout.SOUTH);

		minusButton = new JButton("-");
		minusButton.addActionListener(new MinusButtonActionListener());
		
		plusButton = new JButton("+");
		plusButton.addActionListener(new PlusButtonActionListener());
		jumpPanel.setJumpButtonActionListener(new JumpButtonActionListener());
		
		add(minusButton, BorderLayout.WEST);
		add(plusButton, BorderLayout.EAST);

	}
	
	
	public void refreshDisplayedValues() {
		for (int i = 0; i < MAX_MEMORY_ROWS; i++) {
			String value = addressToValue.get(i + currentDisplayStartAddress);
			Boolean breakpointStatus = addressToBreakpoint.get(i + currentDisplayStartAddress);
			String currentCondition = addressToCurrentCondition.get(i + currentDisplayStartAddress);
			String breakpointValue = addressToBreakpointValue.get(i + currentDisplayStartAddress);
			
			if(value != null && breakpointStatus != null && currentCondition != null && breakpointValue != null ) {
				displayedAddresses.get(i).setText(Integer.toString(i+ currentDisplayStartAddress));
				displayedValues.get(i).setText(value);
				breakpoints.get(i).setSelected(breakpointStatus);
				conditions.get(i).setSelectedItem(currentCondition);
				breakpointValues.get(i).setText(breakpointValue);
			} else {
				displayedAddresses.get(i).setText("");
				displayedValues.get(i).setText("");
				breakpoints.get(i).setSelected(false);
				conditions.get(i).setSelectedItem("");
				breakpointValues.get(i).setText("");
			}
		}
	}
	
	
	public String getDisplayedValueForAddress(Integer address) {
		return addressToValue.get(address);
	}
	
	public void setDisplayedValueForAddress(Integer address, String value) {
		addressToValue.put(address, value);
	}
	
	public boolean getBreakpointForAddress(Integer address) {
		return addressToBreakpoint.get(address);
	}
	
	public void setBreakpointForAddress(Integer address, boolean value) {
		addressToBreakpoint.put(address, value);
	}
	
	public String getBreakpointConditionForAddress(Integer address) {
		return addressToCurrentCondition.get(address);
	}
	
	public void setBreakpointConditionForAddress(Integer address, String value) {
		addressToCurrentCondition.put(address, value);
	}
	
	public String getDisplayedBreakpointValueForAddress(Integer address) {
		return addressToBreakpointValue.get(address);
	}
	
	public void setDisplayedBreakpointValueForAddress(Integer address, String value) {
		addressToBreakpointValue.put(address, value);
	}
	
	
	
	
	private class MinusButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			currentDisplayStartAddress -= 20;
			if(currentDisplayStartAddress < startAddress) {
				currentDisplayStartAddress = startAddress;
			}
			
			refreshDisplayedValues();
			
		}
		
	}
	
	private class PlusButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			currentDisplayStartAddress += 20;
			if(currentDisplayStartAddress >= startAddress + size -20) {
				currentDisplayStartAddress = startAddress + size - 20;
			}
			
			refreshDisplayedValues();
			
		}
		
	}
	
	
	private class JumpButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Integer jumpAddress = null;
			
			try {
				jumpAddress = Integer.parseInt(jumpPanel.getJumpAddressValue());
				
				if (jumpAddress < startAddress ) {
					jumpAddress = startAddress;
				} else if (jumpAddress >= startAddress + size -20) {
					jumpAddress = startAddress + size - 20 ;
				}
				
				currentDisplayStartAddress = jumpAddress;
			} catch (NumberFormatException ex) {
				jumpAddress = currentDisplayStartAddress;
			}
			
			refreshDisplayedValues();
			
		}
		
	}
	
	
	private class BreakpointCheckboxItemListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox breakpoint = (JCheckBox) e.getSource();
			int index = breakpoints.indexOf(breakpoint);
			int address = Integer.parseInt(displayedAddresses.get(index).getText());	
			addressToBreakpoint.put(address, breakpoint.isSelected());
			
		}
		
	}
	
	
	private class ConditionComboboxItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			JComboBox<String> combo = (JComboBox<String>) e.getSource();
			int index = conditions.indexOf(combo);
			int address = Integer.parseInt(displayedAddresses.get(index).getText());
			addressToCurrentCondition.put(address, (String)combo.getSelectedItem());
			
		}
		
	}
	
	private class ConditionValueFieldActionListener implements ActionListener {


		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField field = (JTextField) e.getSource();
			int index = breakpointValues.indexOf(field);
			int address = Integer.parseInt(displayedAddresses.get(index).getText());
			
			try {
				Integer.parseInt(field.getText());
			} catch (NumberFormatException ex) {
				field.setText("");
			}
			addressToBreakpointValue.put(address, field.getText());
		}
		
	}

}
