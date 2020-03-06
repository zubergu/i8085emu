package com.zubergu.i8085emu.gui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.zubergu.i8085emu.gui.components.MemoryDevicePanel;

public class MemoryView extends JFrame {

	private JTabbedPane mainPane;
	private Map<String, MemoryDevicePanel> devicePanelForName = new HashMap<String, MemoryDevicePanel>();

	public MemoryView() {
		setTitle("Memory View");
		setSize(500, 700);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		mainPane = createMainPane();
		add(mainPane);

	}

	private JTabbedPane createMainPane() {
		return new JTabbedPane();
	}

	public void createTabForDevice(String deviceName, int startAddress, int size) {
		MemoryDevicePanel devicePanel = new MemoryDevicePanel(deviceName,
				startAddress, size);
		devicePanelForName.put(deviceName, devicePanel);
		mainPane.addTab(deviceName, null, devicePanel, "Memory view");
	}

	public Integer getDisplayedValueForAddress(String deviceDescription,
			Integer address) {
		Integer val = null;
		try {
			val = Integer.parseInt(devicePanelForName.get(deviceDescription)
				.getDisplayedValueForAddress(address));
		} catch (NumberFormatException ex) {
			val = null;
		}
		return val;
	}

	public void setDisplayedValueForAddress(String deviceDescription,
			Integer address, String value) {
		devicePanelForName.get(deviceDescription).setDisplayedValueForAddress(
				address, value);
	}

	public boolean getBreakpointForAddress(String deviceDescription,
			Integer address) {
		return devicePanelForName.get(deviceDescription)
				.getBreakpointForAddress(address);
	}

	public void setBreakpointForAddress(String deviceDescription,
			Integer address, boolean value) {
		devicePanelForName.get(deviceDescription).setBreakpointForAddress(
				address, value);
	}

	public String getBreakpointConditionForAddress(String deviceDescription,
			Integer address) {
		return devicePanelForName.get(deviceDescription)
				.getBreakpointConditionForAddress(address);
	}

	public void setBreakpointConditionForAddress(String deviceDescription,
			Integer address, String value) {
		devicePanelForName.get(deviceDescription)
				.setBreakpointConditionForAddress(address, value);
	}

	public Integer getDisplayedBreakpointValueForAddress(
			String deviceDescription, Integer address) {
		Integer val = null;
		
		try {
			val = Integer.parseInt(devicePanelForName.get(deviceDescription)
				.getDisplayedBreakpointValueForAddress(address));
		} catch (NumberFormatException ex) {
			val = null;
		}
		return val;
	}

	public void setDisplayedBreakpointValueForAddress(String deviceDescription,
			Integer address, String value) {
		devicePanelForName.get(deviceDescription)
				.setDisplayedBreakpointValueForAddress(address, value);
	}
	
	
	public void refreshDisplayedValuesForDevice(String deviceDescription) {
		devicePanelForName.get(deviceDescription).refreshDisplayedValues();
	}

}
