package com.zubergu.i8085emu.cpu.instructions.logicalgroup;

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

public class XRArTest {
	
	@Test
	public void correctInstruction() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		Register8Bit b = Mockito.mock(Register8Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.get8BitRegisterForName(Register8BitName.B)).thenReturn(b);
		
		when(a.getValue())
		.thenReturn(0x07)
		.thenReturn(0x07 ^ 0x0A);
		
		when(b.getValue()).thenReturn(0x0A);
		
		XRAr instruction = new XRAr(cpu, Register8BitName.B);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(a).setValue(0x07 ^ 0x0A);
		verify(cpu).setCarryFlag(0x00, 1, false);
		verify(cpu).setAuxiliaryCarryFlag(0x00, 1, false);
		verify(cpu).setZeroFlag(0x07 ^ 0x0A);
		verify(cpu).setSignFlag(0x07 ^ 0x0A);
		verify(cpu).setParityFlag(0x07  ^ 0x0A);
	}

}
