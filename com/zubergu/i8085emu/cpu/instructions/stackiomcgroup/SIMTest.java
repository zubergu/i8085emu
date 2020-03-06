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

public class SIMTest {
	
	@Test
	public void correctMasksAllSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0xFF);

		SIM instruction = new SIM(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(cpu).setSerialOutputDataBit(true);
		verify(cpu).setInterrupt7_5pending(false);
		verify(cpu).setInterrupt7_5mask(true);
		verify(cpu).setInterrupt6_5mask(true);
		verify(cpu).setInterrupt5_5mask(true);
	}
	
	@Test
	public void correctMasksNotSetNotEnabled() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0xA7);

		SIM instruction = new SIM(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(cpu, never()).setSerialOutputDataBit(any(Boolean.class));
		verify(cpu, never()).setInterrupt7_5pending(any(Boolean.class));
		verify(cpu, never()).setInterrupt7_5mask(any(Boolean.class));
		verify(cpu, never()).setInterrupt6_5mask(any(Boolean.class));
		verify(cpu, never()).setInterrupt5_5mask(any(Boolean.class));
	}
	
	@Test
	public void correctMasksEveryOtherSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0xAA);

		SIM instruction = new SIM(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		verify(cpu, never()).setSerialOutputDataBit(any(Boolean.class));
		verify(cpu, never()).setInterrupt7_5pending(any(Boolean.class));
		verify(cpu).setInterrupt7_5mask(false);
		verify(cpu).setInterrupt6_5mask(true);
		verify(cpu).setInterrupt5_5mask(false);
	}

}
