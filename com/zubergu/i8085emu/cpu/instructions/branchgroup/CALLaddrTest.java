package com.zubergu.i8085emu.cpu.instructions.branchgroup;

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

public class CALLaddrTest {
	
	@Test
	public void correctInstructionCarrySet() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		Register16Bit sp = Mockito.mock(Register16Bit.class);
		
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get16BitRegisterForName(Register16BitName.SP)).thenReturn(sp);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		
		when(pc.getValue()).thenReturn(0xFF0A).thenReturn(0xFF0B).thenReturn(0xFF0C);
		when(sp.getValue()).thenReturn(0xFFFE).thenReturn(0xFFFD);
		when(m.readByte(0xFF0A)).thenReturn(0x03);
		when(m.readByte(0xFF0B)).thenReturn(0x04);
		
		CALLaddr instruction = new CALLaddr(cpu);
		
		Assert.assertEquals(instruction.execute(), 18);
		
		verify(pc, times(2)).increment();
		verify(sp, times(2)).decrement();
		verify(pc).setValue(0x0403);
		
		verify(m).writeByte(0xFFFE, 0xFF);
		verify(m).writeByte(0xFFFD, 0x0C);
		
	}

}
