package com.zubergu.i8085emu.cpu.instructions.datatransfergroup;

import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.zubergu.i8085emu.cpu.core.Cpu;
import com.zubergu.i8085emu.cpu.registers.Register16Bit;
import com.zubergu.i8085emu.cpu.registers.Register16BitName;
import com.zubergu.i8085emu.cpu.registers.Register8Bit;
import com.zubergu.i8085emu.cpu.registers.Register8BitName;
import com.zubergu.i8085emu.memory.Memory;

public class LDAaddress16Test {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		Register8Bit regA = Mockito.mock(Register8Bit.class);
		Memory memory = Mockito.mock(Memory.class);
		
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.getMemory()).thenReturn(memory);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(regA);
		
		when(pc.getValue()).thenReturn(0x01).thenReturn(0x02);
		
		when(memory.readByte(0x01)).thenReturn(0xEF);
		when(memory.readByte(0x02)).thenReturn(0xAB);
		when(memory.readByte(0xABEF)).thenReturn(0xBB);
		
		LDAaddress16 instruction = new LDAaddress16(cpu);
		
		Assert.assertTrue(instruction.execute() == 13);
		verify(pc, times(2)).increment();
		verify(regA).setValue(0xBB);
		
		
		
	}

}
