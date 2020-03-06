package com.zubergu.i8085emu.devices;

import java.util.ArrayList;
import java.util.List;

import com.zubergu.i8085emu.io.MemoryMappedIODevice;
import com.zubergu.i8085emu.io.PortMappedIODevice;
import com.zubergu.i8085emu.io.RAM;
import com.zubergu.i8085emu.io.ROM;
import com.zubergu.i8085emu.memory.Memory;

public class MemoryImpl implements Memory {
	
	private List<PortMappedIODevice> portMappedDevices = new ArrayList<PortMappedIODevice>();
	private List<ROM> readOnlyMemoryDevices = new ArrayList<ROM>();
	private List<RAM> readWriteMemoryDevices = new ArrayList<RAM>();
	private List<MemoryMappedIODevice> mmIODeviceList = new ArrayList<MemoryMappedIODevice>();
	private List<ROM> nonPortDevices = new ArrayList<ROM>();

	@Override
	public Integer readByte(Integer address) {
		ROM readableMemory = getReadableDeviceForAddress(address);
		if(readableMemory == null) {
			return 0;
		} else {
			return readableMemory.readByte(address);
		}
	}

	@Override
	public void writeByte(Integer address, Integer data) {
		RAM writableMemory = getWritableDeviceForAddress(address);
		if(writableMemory != null) {
			writableMemory.writeByte(address, data);
		}
	}

	@Override
	public Integer readPort(Integer port) {
		PortMappedIODevice pmIO = getDeviceForPort(port);
		if(pmIO == null) {
			return 0;
		} else {
			return pmIO.readPort(port);
		}
	}

	
	
	@Override
	public void writeToPort(Integer port, Integer data) {
		PortMappedIODevice pmIO = getDeviceForPort(port);
		if(pmIO != null) {
			pmIO.writeToPort(port, data);
		}
		
	}

	
	/* connecting devices should be checked for collisions in addresses */
	// TODO
	
	@Override
	public void connect(ROM rom) {
		readOnlyMemoryDevices.add(rom);
		nonPortDevices.add(rom);
		
	}

	@Override
	public void connect(RAM ram) {
		readWriteMemoryDevices.add(ram);
		nonPortDevices.add(ram);
	}

	@Override
	public void connect(MemoryMappedIODevice mmIO) {
		readWriteMemoryDevices.add(mmIO);
		mmIODeviceList.add(mmIO);
		nonPortDevices.add(mmIO);
	}
	
	@Override
	public void connect(PortMappedIODevice pmIO) {
		portMappedDevices.add(pmIO);
		
	}
	
	private ROM getReadableDeviceForAddress(int address) {
		for(ROM device : nonPortDevices) {
			if( address >= device.getStartAddress() && address <= device.getEndAddress()) {
				return device;
			}
		}
		return null;
	}
	
	private RAM getWritableDeviceForAddress(int address) {
		for(RAM device : readWriteMemoryDevices) {
			if( address >= device.getStartAddress() && address <= device.getEndAddress()) {
				return device;
			}
		}
		return null;
	}
	
	private PortMappedIODevice getDeviceForPort(int port) {
		for(PortMappedIODevice device: portMappedDevices) {
			if(device.getPortsUsed().contains(port)) {
				return device;
			}
		}
		return null;
	}

	@Override
	public List<MemoryMappedIODevice> getMemoryMappedIODeviceList() {
		return mmIODeviceList;
	}

	@Override
	public List<PortMappedIODevice> getPortMappedIODeviceList() {
		return portMappedDevices;
	}

	@Override
	public List<RAM> getRamChipList() {
		return readWriteMemoryDevices;
	}

	@Override
	public List<ROM> getRomChipList() {
		return readOnlyMemoryDevices;
	}

	@Override
	public void initialize(int address, int data) {
		
		ROM device = getReadableDeviceForAddress(address);
		RAM device2 = getWritableDeviceForAddress(address);
		if(device != null) {
			device.initialize(address, data);
		}
		
		if(device2 != null) {
			device2.initialize(address,data);
		}
		
	}

	@Override
	public List<ROM> getAllNonPortMappedDevices() {
		return nonPortDevices;
	}

}
