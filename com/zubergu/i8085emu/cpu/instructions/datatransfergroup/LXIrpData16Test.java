package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

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

public class LXIrpData16Test {
	
	@Test
	public void correctInstructionRP() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory memory = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		Register8Bit c = Mockito.mock(Register8Bit.class);
		RegisterPair bc = Mockito.mock(RegisterPair.class);
		
		
		when(cpu.getMemory()).thenReturn(memory);
		when(pc.getValue()).thenReturn(0x00).thenReturn(0x01);
		when(memory.readByte(0x00)).thenReturn(0xAB);
		when(memory.readByte(0x01)).thenReturn(0xEF);
		
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.getRegisterPairForName(RegisterPairName.BC)).thenReturn(bc);
		when(bc.getRh()).thenReturn(b);
		when(bc.getRl()).thenReturn(c);
		LXIrpData16 instruction = new LXIrpData16(cpu, RegisterPairName.BC);
		
		Assert.assertEquals(instruction.execute(), 10);
		verify(pc, times(2)).increment();
		verify(c).setValue(0xAB);
		verify(b).setValue(0xEF);
	}
	
	@Test
	public void correctInstructionSP() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory memory = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		
		
		when(cpu.getMemory()).thenReturn(memory);
		when(pc.getValue()).thenReturn(0x00).thenReturn(0x01);
		when(memory.readByte(0x00)).thenReturn(0xAB);
		when(memory.readByte(0x01)).thenReturn(0xEF);
		
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		
		LXIrpData16 instruction = new LXIrpData16(cpu, Register16BitName.SP);
		
		Assert.assertEquals(instruction.execute(), 10);
		verify(pc, times(2)).increment();
		verify(sp).setValue(0xEFAB);
	}
}
