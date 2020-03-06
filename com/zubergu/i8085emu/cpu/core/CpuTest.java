package com.zubergu.i8085emu.cpu.core;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class CpuTest {
	
	@Test
	public void correct8BitRegisterReturnedForName() {
		
		Cpu cpu = new Cpu();
		
		Assert.assertTrue(cpu.get8BitRegisterForName(Register8BitName.A) instanceof Register8Bit);
		
	}
	
	@Test
	public void correct16BitRegisterReturnedForName() {
		
		Cpu cpu = new Cpu();
		
		Assert.assertTrue(cpu.get16BitRegisterForName(Register16BitName.PC) instanceof Register16Bit);
		
	}
	
	@Test
	public void correctRegisterPairReturnedForName() {
		
		Cpu cpu = new Cpu();
		
		Assert.assertTrue(cpu.getRegisterPairForName(RegisterPairName.APSW) instanceof RegisterPair);
		
	}
	
	@Test
	public void correctActionsWhenExecutingNextInstruction() {
		Cpu cpu = new Cpu();
		Memory memory = Mockito.mock(Memory.class);
		cpu.connectMemory(memory);
		
		cpu.executeNextInstruction();
	}
	
	
	@Test
	public void zeroFlagSetTest() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.zeroFlagIsSet());
		cpu.setZeroFlag(0x00);
		Assert.assertTrue(cpu.zeroFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x40);
	}
	
	@Test
	public void zeroFlagResetTest() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x40);
		Assert.assertTrue(cpu.zeroFlagIsSet());
		cpu.setZeroFlag(0x01);
		Assert.assertFalse(cpu.zeroFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	@Test
	public void carryFlagSetTestAdd() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.carryFlagIsSet());
		cpu.setCarryFlag(0xFF, 0x02, false);
		Assert.assertTrue(cpu.carryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x01);
	}
	
	@Test
	public void carryFlagResetTestAdd() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x01);
		Assert.assertTrue(cpu.carryFlagIsSet());
		cpu.setCarryFlag(0xFE, 0x01, false);
		Assert.assertFalse(cpu.carryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	@Test
	public void carryFlagSetTestSub() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.carryFlagIsSet());
		cpu.setCarryFlag(0x01, 0x02, true);
		Assert.assertTrue(cpu.carryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x01);
	}
	
	@Test
	public void carryFlagResetTestSub() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x01);
		Assert.assertTrue(cpu.carryFlagIsSet());
		cpu.setCarryFlag(0x02, 0x02, true);
		Assert.assertFalse(cpu.carryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	@Test
	public void auxiliaryCarryFlagSetTestAdd() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.auxiliaryCarryFlagIsSet());
		cpu.setAuxiliaryCarryFlag(0x0F, 0x01, false);
		Assert.assertTrue(cpu.auxiliaryCarryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x10);
	}
	
	@Test
	public void auxiliaryCarryFlagResetTestAdd() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x10);
		Assert.assertTrue(cpu.auxiliaryCarryFlagIsSet());
		cpu.setAuxiliaryCarryFlag(0xFE, 0x01, false);
		Assert.assertFalse(cpu.auxiliaryCarryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	@Test
	public void auxiliaryCarryFlagSetTestSub() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.auxiliaryCarryFlagIsSet());
		cpu.setAuxiliaryCarryFlag(0x01, 0x02, true);
		Assert.assertTrue(cpu.auxiliaryCarryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x10);
	}
	
	@Test
	public void auxiliaryCarryFlagResetTestSub() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x10);
		Assert.assertTrue(cpu.auxiliaryCarryFlagIsSet());
		cpu.setAuxiliaryCarryFlag(0x02, 0x02, true);
		Assert.assertFalse(cpu.auxiliaryCarryFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	@Test
	public void parityFlagSet() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.parityFlagIsSet());
		cpu.setParityFlag(0xFF);
		Assert.assertTrue(cpu.parityFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x04);
	}
	
	@Test
	public void parityFlagReset() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x04);
		Assert.assertTrue(cpu.parityFlagIsSet());
		cpu.setParityFlag(0xFE);
		Assert.assertFalse(cpu.parityFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	@Test
	public void signFlagSet() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x00);
		Assert.assertFalse(cpu.signFlagIsSet());
		cpu.setSignFlag(0xFF);
		Assert.assertTrue(cpu.signFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x80);
	}
	
	@Test
	public void signFlagReset() {
		Cpu cpu = new Cpu();
		Register8Bit psw = cpu.get8BitRegisterForName(Register8BitName.PSW);
		psw.setValue(0x80);
		Assert.assertTrue(cpu.signFlagIsSet());
		cpu.setSignFlag(0x7F);
		Assert.assertFalse(cpu.signFlagIsSet());
		Assert.assertEquals(psw.getValue(), 0x00);
	}
	
	
	

}
