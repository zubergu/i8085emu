package com.zubergu.i8085emu.devices;

import com.zubergu.i8085emu.io.RAM;

public class RamImpl implements RAM {
	private int startAddress;
	private int size;
	private int endAddress;
	private String name;
	private int[] memory;
	
	public RamImpl(int startAddress, int size, String name) {
		this.startAddress = startAddress;
		this.size = size;
		this.endAddress = (startAddress + size) - 1;
		memory = new int[size];
		this.name = name;
	}

	@Override
	public int readByte(int address) {
		return memory[address-startAddress] & 0xFF;
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
	public void writeByte(int address, int data) {
		memory[address-startAddress] = data & 0xFF;
		
	}

	@Override
	public String getDescription() {
		return name + " " + startAddress +" - " + endAddress;
	}

	@Override
	public void initialize(int address, int data) {
		writeByte(address, data);
		
	}

}
