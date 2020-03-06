package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;

public class CMCTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		when(cpu.carryFlagIsSet()).thenReturn(true);
		
		CMC instruction = new CMC(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(cpu).setCarryFlag(0x00, 0x01, false);
	}
	
	@Test
	public void correctInstructionCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		when(cpu.carryFlagIsSet()).thenReturn(false);
		
		CMC instruction = new CMC(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(cpu).setCarryFlag(0xFF, 0x01, false);
		
	}
	

}
