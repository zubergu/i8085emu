package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class SBIdataTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.carryFlagIsSet()).thenReturn(true);
		
		when(pc.getValue()).thenReturn(0x00FF);
		when(a.getValue())
			.thenReturn(0x0F)
			.thenReturn(0x0F)
			.thenReturn(0x0F)
			.thenReturn(0x04);
			;
		
		when(m.readByte(0x00FF)).thenReturn(0x0A);
		
		SBIdata instruction = new SBIdata(cpu);
		
		Assert.assertEquals(instruction.execute(), 7);
		verify(pc).increment();
		verify(a).setValue(0x04);
		verify(cpu).setCarryFlag(0x0F, 0x0B, true);
		verify(cpu).setAuxiliaryCarryFlag(0x0F, 0x0B, true);
		verify(cpu).setZeroFlag(0x04);
		verify(cpu).setSignFlag(0x04);
		verify(cpu).setParityFlag(0x04);
	}
	
	@Test
	public void correctInstructionCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.carryFlagIsSet()).thenReturn(false);
		
		when(pc.getValue()).thenReturn(0x00FF);
		when(a.getValue())
			.thenReturn(0x0F)
			.thenReturn(0x0F)
			.thenReturn(0x0F)
			.thenReturn(0x05);
		
		when(m.readByte(0x00FF)).thenReturn(0x0A);
		
		SBIdata instruction = new SBIdata(cpu);
		
		Assert.assertEquals(instruction.execute(), 7);
		verify(pc).increment();
		verify(a).setValue(0x05);
		verify(cpu).setCarryFlag(0x0F, 0x0A, true);
		verify(cpu).setAuxiliaryCarryFlag(0x0F, 0x0A, true);
		verify(cpu).setZeroFlag(0x05);
		verify(cpu).setSignFlag(0x05);
		verify(cpu).setParityFlag(0x05);
	}
}
