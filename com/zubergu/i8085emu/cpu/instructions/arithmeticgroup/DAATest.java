package com.zubergu.i8085emu.cpu.instructions.arithmeticgroup;


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
import com.zubergu.i8085emu.memory.Memory;

public class DAATest {
	
	@Test
	public void correctInstructionExecutionWhenAcSetCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.auxiliaryCarryFlagIsSet()).thenReturn(true);
		when(cpu.carryFlagIsSet()).thenReturn(false);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0x20).thenReturn(0x26);
		
		DAA instruction = new DAA(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		
		
		verify(cpu).setAuxiliaryCarryFlag(0x00, 0x06, false);
		verify(cpu).setCarryFlag(0x20, 0x06, false);
		verify(cpu).setZeroFlag(0x26);
		verify(cpu).setParityFlag(0x26);
		verify(cpu).setSignFlag(0x26);
		verify(a, times(2)).setValue(0x26);
		
		
	}
	
	@Test
	public void correctInstructionExecutionWhenAcNotSetCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register8Bit a = Mockito.mock(Register8Bit.class);
		
		when(cpu.auxiliaryCarryFlagIsSet()).thenReturn(false);
		when(cpu.carryFlagIsSet()).thenReturn(false);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(a);
		when(a.getValue()).thenReturn(0x2B).thenReturn(0x31); // 
		
		DAA instruction = new DAA(cpu);
		
		Assert.assertEquals(instruction.execute(), 4);
		verify(cpu).setAuxiliaryCarryFlag(0x0B, 0x06, false);
		verify(cpu).setCarryFlag(0x2B, 0x06, false);
		
		verify(cpu).setZeroFlag(0x31);
		verify(cpu).setParityFlag(0x31);
		verify(cpu).setSignFlag(0x31);
		
		verify(a, times(2)).setValue(0x31);
		
	}
	
	

}

