package com.zubergu.i8085emu.io;

public interface SerialIODevice {
	public boolean readSerialOUT();
	public void setSerialIN(boolean value);
	public void update();
	public String getDescription();
}
