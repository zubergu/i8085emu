package com.zubergu.i8085emu.cpu.registers;

public class Register8Bit {
	private int value = 0;
	
	public Register8Bit() {}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int newValue) {
		value = newValue & 0xFF;
	}
	
	public void increment() {
		value = (value+1) & 0xFF;
	}
	
	public void decrement() {
		value = (value-1) & 0xFF;
	}
}
