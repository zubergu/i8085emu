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

public class ADCrTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.get8BitRegisterForName(Register8BitName.B)).thenReturn(b);
		when(cpu.carryFlagIsSet()).thenReturn(true);
		
		when(a.getValue()).thenReturn(0x0F).thenReturn(0x0F).thenReturn(0x0F).thenReturn(0x0F+0x0A+1);
		when(b.getValue()).thenReturn(0x0A);
		when(m.readByte(0xFF)).thenReturn(0x0A);
		
		ADCr instruction = new ADCr(cpu, Register8BitName.B);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(a).setValue(0x0F + 0x0A + 1);
		verify(cpu).setCarryFlag(0x0F, 0x0B, false);
		verify(cpu).setAuxiliaryCarryFlag(0x0F, 0x0B, false);
		verify(cpu).setZeroFlag(0x0F + 0x0A + 1);
		verify(cpu).setSignFlag(0x0F + 0x0A + 1);
		verify(cpu).setParityFlag(0x0F + 0x0A + 1);
	}
	
	
	@Test
	public void correctInstructionCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.get8BitRegisterForName(Register8BitName.B)).thenReturn(b);
		when(cpu.carryFlagIsSet()).thenReturn(false);
		
		when(a.getValue()).thenReturn(0x0F).thenReturn(0x0F).thenReturn(0x0F).thenReturn(0x0F+0x0A);
		when(b.getValue()).thenReturn(0x0A);
		when(m.readByte(0xFF)).thenReturn(0x0A);
		
		ADCr instruction = new ADCr(cpu, Register8BitName.B);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(a).setValue(0x0F + 0x0A + 0);
		verify(cpu).setCarryFlag(0x0F, 0x0A, false);
		verify(cpu).setAuxiliaryCarryFlag(0x0F, 0x0A, false);
		verify(cpu).setZeroFlag(0x0F + 0x0A + 0);
		verify(cpu).setSignFlag(0x0F + 0x0A + 0);
		verify(cpu).setParityFlag(0x0F + 0x0A + 0);
	}

}
