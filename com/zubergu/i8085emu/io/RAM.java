package com.zubergu.i8085emu.io;

public interface RAM extends ROM {
	public void writeByte(int address, int data);
}
