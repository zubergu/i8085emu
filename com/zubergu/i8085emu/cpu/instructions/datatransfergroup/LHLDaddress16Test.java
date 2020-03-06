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
import com.zubergu.i8085emu.memory.Memory;

public class LHLDaddress16Test {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Memory m = Mockito.mock(Memory.class);
		Register8Bit h = Mockito.mock(Register8Bit.class);
		Register8Bit l = Mockito.mock(Register8Bit.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
	
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.getMemory()).thenReturn(m);
		when(cpu.get8BitRegisterForName(Register8BitName.H)).thenReturn(h);
		when(cpu.get8BitRegisterForName(Register8BitName.L)).thenReturn(l);
	
		when(pc.getValue()).thenReturn(0x01).thenReturn(0x02);
		
		when(m.readByte(0x01)).thenReturn(0xFE);
		when(m.readByte(0x02)).thenReturn(0xBC);
		
		when(m.readByte(0xBCFE)).thenReturn(0xBB);
		when(m.readByte(0xBCFF)).thenReturn(0xAA);
		
		
		LHLDaddress16 instruction = new LHLDaddress16(cpu);
		
		Assert.assertTrue(instruction.execute() == 16);
		
		verify(pc, times(2)).increment();
		verify(l).setValue(0xBB);
		verify(h).setValue(0xAA);
		
	}

}
