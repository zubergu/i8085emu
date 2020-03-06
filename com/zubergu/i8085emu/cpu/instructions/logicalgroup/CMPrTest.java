package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;

public class CMPrTest {
	
	@Test
	public void correctInstructionExecution() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.get8BitRegisterForName(Register8BitName.B)).thenReturn(b);
		when(a.getValue()).thenReturn(0x0B);
		when(b.getValue()).thenReturn(0x0A);
		
		CMPr instruction = new CMPr(cpu, Register8BitName.B);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(cpu).setCarryFlag(0x0B, 0x0A, true);
		verify(cpu).setAuxiliaryCarryFlag(0x0B, 0x0A, true);
		verify(cpu).setZeroFlag(0x01);
		verify(cpu).setParityFlag(0x01);
		verify(cpu).setSignFlag(0x01);
		
	}
	

}
