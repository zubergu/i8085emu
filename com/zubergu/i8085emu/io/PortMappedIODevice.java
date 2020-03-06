package com.zubergu.i8085emu.io;

import java.util.List;

public interface PortMappedIODevice {
	public void writeToPort(int port, int data);
	public int readPort(int port);
	public List<Integer> getPortsUsed();
	public void update();
	public String getDescription();
}
