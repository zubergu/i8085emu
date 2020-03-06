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

public class RARTest {
	
	@Test
	public void correctInstructionCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);

		when(cpu.carryFlagIsSet()).thenReturn(false);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0xFF);

		
		RAR instruction = new RAR(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(a).setValue(0x7F);
		verify(cpu).setCarryFlag(0xFF, 1, false);
	}
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);

		when(cpu.carryFlagIsSet()).thenReturn(true);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0xFE);

		
		RAR instruction = new RAR(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(a).setValue(0xFF);
		verify(cpu).setCarryFlag(0x00, 1, false);
	}

}
