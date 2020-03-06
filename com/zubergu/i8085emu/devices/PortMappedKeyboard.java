package com.zubergu.i8085emu.devices;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zubergu.i8085emu.io.PortMappedIODevice;

public class PortMappedKeyboard implements KeyListener,PortMappedIODevice {
	private static final Integer STATUS_PORT_NUMBER = 0xFA;
	private static final Integer DATA_PORT_NUMBER = 0xFC;
	
	private Map<Integer, Integer> ports = new HashMap<Integer, Integer> ();
	private List<Integer> portNumbersUsed = new ArrayList<Integer>();
	
	public PortMappedKeyboard() {
		ports.put(STATUS_PORT_NUMBER, 0x01); // status port
		ports.put(DATA_PORT_NUMBER, 0); // data port
		portNumbersUsed.add(STATUS_PORT_NUMBER);
		portNumbersUsed.add(DATA_PORT_NUMBER);
	}

	@Override
	public void writeToPort(int port, int data) {
		if(portNumbersUsed.contains(port)) {
			ports.put(port, data); // overwrite port value if this port is used
		}
		
	}

	@Override
	public int readPort(int port) {
		if(portNumbersUsed.contains(port)) {
			if(port == DATA_PORT_NUMBER) {
				ports.put(STATUS_PORT_NUMBER, 0x01);
			}
			return ports.get(port);
		}
		return 0;
	}

	@Override
	public List<Integer> getPortsUsed() {
		return portNumbersUsed;
	}

	@Override
	public void update() {
	}

	@Override
	public String getDescription() {
		return "PORT MAPPED KEYBOARD";
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		ports.put(DATA_PORT_NUMBER, e.getKeyChar() & 0xFF);
		ports.put(STATUS_PORT_NUMBER, 0x00);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		ports.put(STATUS_PORT_NUMBER, 0x01);
	}

}
