package com.zubergu.i8085emu.cpu.registers;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Register16BitTest {
	
	@Test
	public void setAndGetValuesCorrect() {
		Register16Bit register = new Register16Bit();
		register.setValue(0xFFFF);
		
		Assert.assertEquals(register.getValue(), 0xFFFF);
	}
	
	@Test
	public void setAndGetValuesTrimmedCorrect() {
		Register16Bit register = new Register16Bit();
		register.setValue(0xABCDFFFF);
		
		Assert.assertEquals(register.getValue(), 0xFFFF);
	}
	
	@Test
	public void incAndGetValuesCorrect() {
		Register16Bit register = new Register16Bit();
		register.setValue(0xFFFE);
		register.increment();
		Assert.assertEquals(register.getValue(), 0xFFFF);
	}
	
	@Test
	public void incAndGetValuesTrimmedCorrect() {
		Register16Bit register = new Register16Bit();
		register.setValue(0xFFFF);
		register.increment();
		
		Assert.assertEquals(register.getValue(), 0x0000);
	}
	
	@Test
	public void decAndGetValuesCorrect() {
		Register16Bit register = new Register16Bit();
		register.setValue(0x0001);
		register.decrement();
		Assert.assertEquals(register.getValue(), 0x0000);
	}
	
	@Test
	public void decAndGetValuesTrimmedCorrect() {
		Register16Bit register = new Register16Bit();
		register.setValue(0x0000);
		register.decrement();
		
		Assert.assertEquals(register.getValue(), 0xFFFF);
	}

}
