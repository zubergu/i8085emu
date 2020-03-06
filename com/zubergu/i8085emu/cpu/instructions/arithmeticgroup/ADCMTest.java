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
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class ADCMTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(cpu.carryFlagIsSet()).thenReturn(true);
		
		when(a.getValue()).thenReturn(0x07).thenReturn(0x07).thenReturn(0x07).thenReturn(0x07+0x03+1);
		when(hl.getValue()).thenReturn(0x0A);
		when(m.readByte(0x0A)).thenReturn(0x03);
		
		ADCM instruction = new ADCM(cpu);
		
		Assert.assertEquals(instruction.execute(), 7);
		verify(a).setValue(0x07 + 0x03 + 1);
		verify(cpu).setCarryFlag(0x07, 0x04, false);
		verify(cpu).setAuxiliaryCarryFlag(0x07, 0x04, false);
		verify(cpu).setZeroFlag(0x07 + 0x03 + 1);
		verify(cpu).setSignFlag(0x07 + 0x03 + 1);
		verify(cpu).setParityFlag(0x07 + 0x03 + 1);
	}
	
	
	@Test
	public void correctInstructionCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(cpu.carryFlagIsSet()).thenReturn(false);
		
		when(a.getValue()).thenReturn(0x07).thenReturn(0x07).thenReturn(0x07).thenReturn(0x07+0x03);
		when(hl.getValue()).thenReturn(0x0A);
		when(m.readByte(0x0A)).thenReturn(0x03);
		
		ADCM instruction = new ADCM(cpu);
		
		Assert.assertEquals(instruction.execute(), 7);
		verify(a).setValue(0x07 + 0x03);
		verify(cpu).setCarryFlag(0x07, 0x03, false);
		verify(cpu).setAuxiliaryCarryFlag(0x07, 0x03, false);
		verify(cpu).setZeroFlag(0x07 + 0x03);
		verify(cpu).setSignFlag(0x07 + 0x03);
		verify(cpu).setParityFlag(0x07 + 0x03);
	}

}
