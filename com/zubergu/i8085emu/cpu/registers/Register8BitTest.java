package com.zubergu.i8085emu.cpu.registers;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Register8BitTest {
	
	@Test
	public void setAndGetValuesCorrect() {
		Register8Bit register = new Register8Bit();
		register.setValue(0xFF);
		
		Assert.assertEquals(register.getValue(), 0xFF);
	}
	
	@Test
	public void setAndGetValuesTrimmedCorrect() {
		Register8Bit register = new Register8Bit();
		register.setValue(0xABCDFFDE);
		
		Assert.assertEquals(register.getValue(), 0xDE);
	}
	
	@Test
	public void incAndGetValuesCorrect() {
		Register8Bit register = new Register8Bit();
		register.setValue(0xFE);
		register.increment();
		Assert.assertEquals(register.getValue(), 0xFF);
	}
	
	@Test
	public void incAndGetValuesTrimmedCorrect() {
		Register8Bit register = new Register8Bit();
		register.setValue(0xFF);
		register.increment();
		
		Assert.assertEquals(register.getValue(), 0x00);
	}
	
	@Test
	public void decAndGetValuesCorrect() {
		Register8Bit register = new Register8Bit();
		register.setValue(0x01);
		register.decrement();
		Assert.assertEquals(register.getValue(), 0x00);
	}
	
	@Test
	public void decAndGetValuesTrimmedCorrect() {
		Register8Bit register = new Register8Bit();
		register.setValue(0x00);
		register.decrement();
		
		Assert.assertEquals(register.getValue(), 0xFF);
	}

}
