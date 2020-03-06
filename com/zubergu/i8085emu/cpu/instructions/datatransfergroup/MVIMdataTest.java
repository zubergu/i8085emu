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
import com.zubergu.i8085emu.cpu.registers.RegisterPair;
import com.zubergu.i8085emu.cpu.registers.RegisterPairName;
import com.zubergu.i8085emu.memory.Memory;

public class MVIMdataTest {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		Memory memory = Mockito.mock(Memory.class);
		
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(cpu.getMemory()).thenReturn(memory);
		
		when(pc.getValue()).thenReturn(0xFAFB);
		when(memory.readByte(0xFAFB)).thenReturn(0xBC);
		when(hl.getValue()).thenReturn(0xABAC);
		
		MVIMdata instruction = new MVIMdata(cpu);
		
		Assert.assertTrue(instruction.execute() == 10);
		verify(pc).increment();
		verify(memory).writeByte(0xABAC, 0xBC);
		
		
		
	}

}
