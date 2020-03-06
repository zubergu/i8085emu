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

public class MOVMRsTest {
	
	@Test
	public void executionWorksCorrectly() {
		Cpu cpu = Mockito.mock(Cpu.class);
		Register16Bit pc = Mockito.mock(Register16Bit.class);
		Register8Bit regA = Mockito.mock(Register8Bit.class);
		RegisterPair hl = Mockito.mock(RegisterPair.class);
		Memory memory = Mockito.mock(Memory.class);
		
		when(cpu.getRegisterPairForName(RegisterPairName.HL)).thenReturn(hl);
		when(cpu.get16BitRegisterForName(Register16BitName.PC)).thenReturn(pc);
		when(cpu.getMemory()).thenReturn(memory);
		when(cpu.get8BitRegisterForName(Register8BitName.A)).thenReturn(regA);
		
		when(hl.getValue()).thenReturn(0xFAFB);
		when(regA.getValue()).thenReturn(0xAB);
		
		MOVMRs instruction = new MOVMRs(cpu, Register8BitName.A);
		
		Assert.assertTrue(instruction.execute() == 7);
		verify(memory).writeByte(0xFAFB, 0xAB);
		
		
		
	}

}
