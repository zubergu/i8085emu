package com.zubergu.i8085emu.cpu.registers;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterPairTest {
	
	@Test
	public void setAndGetValuesCorrect() {
		Register8Bit higher = new Register8Bit();
		Register8Bit lower = new Register8Bit();
		RegisterPair register = new RegisterPair(higher, lower);
		
		register.setValue(0xABCD);
		
		Assert.assertEquals(register.getValue(), 0xABCD);
		Assert.assertEquals(higher.getValue(), 0xAB);
		Assert.assertEquals(lower.getValue(), 0xCD);

	}
	
	@Test
	public void setAndGetValuesTrimmedCorrect() {
		Register8Bit higher = new Register8Bit();
		Register8Bit lower = new Register8Bit();
		RegisterPair register = new RegisterPair(higher, lower);
		
		register.setValue(0xFFFFABCD);
		
		Assert.assertEquals(register.getValue(), 0xABCD);
		Assert.assertEquals(higher.getValue(), 0xAB);
		Assert.assertEquals(lower.getValue(), 0xCD);
	}
	
	@Test
	public void incAndGetValuesCorrect() {
		Register8Bit higher = new Register8Bit();
		Register8Bit lower = new Register8Bit();
		RegisterPair register = new RegisterPair(higher, lower);
		
		register.setValue(0xABCD);
		register.increment();
		
		Assert.assertEquals(register.getValue(), 0xABCE);
		Assert.assertEquals(higher.getValue(), 0xAB);
		Assert.assertEquals(lower.getValue(), 0xCE);
	}
	
	@Test
	public void incAndGetValuesTrimmedCorrect() {
		Register8Bit higher = new Register8Bit();
		Register8Bit lower = new Register8Bit();
		RegisterPair register = new RegisterPair(higher, lower);
		
		register.setValue(0xFFFF);
		register.increment();
		
		Assert.assertEquals(register.getValue(), 0x0000);
		Assert.assertEquals(higher.getValue(), 0x00);
		Assert.assertEquals(lower.getValue(), 0x00);
	}
	
	@Test
	public void decAndGetValuesCorrect() {
		Register8Bit higher = new Register8Bit();
		Register8Bit lower = new Register8Bit();
		RegisterPair register = new RegisterPair(higher, lower);
		
		register.setValue(0x0001);
		register.decrement();
		
		Assert.assertEquals(register.getValue(), 0x0000);
		Assert.assertEquals(higher.getValue(), 0x00);
		Assert.assertEquals(lower.getValue(), 0x00);
	}
	
	@Test
	public void decAndGetValuesTrimmedCorrect() {
		Register8Bit higher = new Register8Bit();
		Register8Bit lower = new Register8Bit();
		RegisterPair register = new RegisterPair(higher, lower);
		
		register.setValue(0x0000);
		register.decrement();
		
		Assert.assertEquals(register.getValue(), 0xFFFF);
		Assert.assertEquals(higher.getValue(), 0xFF);
		Assert.assertEquals(lower.getValue(), 0xFF);
	}

}
