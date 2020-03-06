package com.zubergu.i8085emu.cpu.registers;

public class Register16Bit {
	private int value = 0;
	
	public Register16Bit() {}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int newValue) {
		value = newValue & 0xFFFF;
	}
	
	public void increment() {
		value = (value+1) & 0xFFFF;
	}

	public void decrement() {
		value = (value-1) & 0xFFFF;
		
	}

}
