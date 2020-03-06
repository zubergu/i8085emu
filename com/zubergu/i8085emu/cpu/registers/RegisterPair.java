package com.zubergu.i8085emu.cpu.registers;

public class RegisterPair {

	private Register8Bit rh;
	private Register8Bit rl;

	public RegisterPair(Register8Bit rh, Register8Bit rl) {
		this.rh = rh;
		this.rl = rl;
	}

	public Register8Bit getRh() {
		return rh;
	}

	public Register8Bit getRl() {
		return rl;
	}

	public int getValue() {
		int value = ((rh.getValue() << 8) | rl.getValue()) & 0xFFFF;
		return value;
	}
	
	public void setValue(int value) {
		rl.setValue(value & 0xFF);
		rh.setValue((value>>8) & 0xFF);
	}
	
	public void increment() {
		int value = getValue();
		value++;
		rl.setValue(value & 0xFF);
		rh.setValue((value>>8) & 0xFF);
	}

	public void decrement() {
		int value = getValue();
		value--;
		rl.setValue(value & 0xFF);
		rh.setValue((value>>8) & 0xFF);
	}

}
