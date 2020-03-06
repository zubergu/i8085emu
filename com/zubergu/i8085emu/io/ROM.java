package com.zubergu.i8085emu.io;

public interface ROM {
	public int readByte(int address);
	
	public int getStartAddress();
	
	public int getMemorySize();
	
	public int getEndAddress();
	
	public String getDescription();
	
	public void initialize(int address, int data);
}
