package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class CMPMTest {
	
	@Test
	public void correctInstructionExecution() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(a.getValue()).thenReturn(0x0B);
		when(hl.getValue()).thenReturn(0xFAFB);
		when(m.readByte(0xFAFB)).thenReturn(0xA);
		
		CMPM instruction = new CMPM(cpu);
		
		Assert.assertEquals(instruction.execute(), 7);
		verify(cpu).setCarryFlag(0x0B, 0x0A, true);
		verify(cpu).setAuxiliaryCarryFlag(0x0B, 0x0A, true);
		verify(cpu).setZeroFlag(0x01);
		verify(cpu).setParityFlag(0x01);
		verify(cpu).setSignFlag(0x01);
		
	}
	
}
