package com.zubergu.i8085emu.cpu.instructions.stackiomcgroup;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

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

public class RIMTest {
	
	@Test
	public void correctMasksAreReadAllSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getInterrupt5_5mask()).thenReturn(true);
		when(cpu.getInterrupt6_5mask()).thenReturn(true);
		when(cpu.getInterrupt7_5mask()).thenReturn(true);
		when(cpu.getInterruptsEnabled()).thenReturn(true);
		when(cpu.getInterrupt5_5pending()).thenReturn(true);
		when(cpu.getInterrupt6_5pending()).thenReturn(true);
		when(cpu.getInterrupt7_5pending()).thenReturn(true);
		when(cpu.getSerialInputDataBit()).thenReturn(true);

		
		RIM instruction = new RIM(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(a).setValue(0xFF);
	}
	
	@Test
	public void correctMasksAreReadNoneSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getInterrupt5_5mask()).thenReturn(false);
		when(cpu.getInterrupt6_5mask()).thenReturn(false);
		when(cpu.getInterrupt7_5mask()).thenReturn(false);
		when(cpu.getInterruptsEnabled()).thenReturn(false);
		when(cpu.getInterrupt5_5pending()).thenReturn(false);
		when(cpu.getInterrupt6_5pending()).thenReturn(false);
		when(cpu.getInterrupt7_5pending()).thenReturn(false);
		when(cpu.getSerialInputDataBit()).thenReturn(false);

		
		RIM instruction = new RIM(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(a).setValue(0x00);
	}
	
	@Test
	public void correctMasksAreReadEveryOtherSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(cpu.getInterrupt5_5mask()).thenReturn(false);
		when(cpu.getInterrupt6_5mask()).thenReturn(true);
		when(cpu.getInterrupt7_5mask()).thenReturn(false);
		when(cpu.getInterruptsEnabled()).thenReturn(true);
		when(cpu.getInterrupt5_5pending()).thenReturn(false);
		when(cpu.getInterrupt6_5pending()).thenReturn(true);
		when(cpu.getInterrupt7_5pending()).thenReturn(false);
		when(cpu.getSerialInputDataBit()).thenReturn(true);

		
		RIM instruction = new RIM(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(a).setValue(0xAA);
	}

}
