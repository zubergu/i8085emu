package com.zubergu.i8085emu.devices;

import com.zubergu.i8085emu.io.ROM;

public class RomImpl implements ROM {
	private int startAddress;
	private int size;
	private int endAddress;
	private int[] memory;
	private String name;
	
	
	public RomImpl(int startAddress, int size, String name) {
		this.startAddress= startAddress;
		this.size = size;
		this.endAddress = (startAddress + size) -1;
		this.name = name;
		this.memory = new int[size];
	}

	@Override
	public int readByte(int address) {
		return memory[address - startAddress] & 0xFF;
	}

	@Override
	public int getStartAddress() {
		return startAddress;
	}

	@Override
	public int getMemorySize() {
		return size;
	}

	@Override
	public int getEndAddress() {
		return endAddress;
	}

	@Override
	public String getDescription() {
		return name + " " + startAddress +" - " + endAddress;
	}

	@Override
	public void initialize(int address, int data) {
		memory[address - startAddress] = data & 0xFF;
		
	}

}
