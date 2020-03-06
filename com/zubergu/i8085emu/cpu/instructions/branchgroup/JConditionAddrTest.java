package com.zubergu.i8085emu.cpu.instructions.branchgroup;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.instructions.JumpCondition;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.memory.Memory;

public class JConditionAddrTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		
		when(cpu.carryFlagIsSet()).thenReturn(true);
		
		when(pc.getValue()).thenReturn(0xFF0A).thenReturn(0xFF0B).thenReturn(0xFF0C);
		when(m.readByte(0xFF0A)).thenReturn(0x03);
		when(m.readByte(0xFF0B)).thenReturn(0x04);
		
		JConditionAddr instruction = new JConditionAddr(cpu, JumpCondition.C);
		
		Assert.assertEquals(instruction.execute(), 10);
		
		verify(pc, times(2)).increment();
		verify(pc).setValue(0x0403);
		
	}
	
	@Test
	public void correctInstructionCarryNotSet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		
		when(cpu.carryFlagIsSet()).thenReturn(false);
		
		when(pc.getValue()).thenReturn(0xFF0A).thenReturn(0xFF0B).thenReturn(0xFF0C);
		when(sp.getValue()).thenReturn(0xFFFE).thenReturn(0xFFFD);
		when(m.readByte(0xFF0A)).thenReturn(0x03);
		when(m.readByte(0xFF0B)).thenReturn(0x04);
		
		JConditionAddr instruction = new JConditionAddr(cpu, JumpCondition.C);
		
		Assert.assertEquals(instruction.execute(), 7);
		
		verify(pc, times(2)).increment();
		verify(pc, never()).setValue(any(Integer.class));
		
		verify(m, never()).writeByte(any(Integer.class), any(Integer.class));
		verify(m, never()).writeByte(any(Integer.class), any(Integer.class));
		
	}

}
